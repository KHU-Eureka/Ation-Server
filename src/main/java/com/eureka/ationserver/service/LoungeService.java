package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.CommonException;
import com.eureka.ationserver.dto.lounge.EMemberStatus;
import com.eureka.ationserver.dto.lounge.LoungeChatResponse;
import com.eureka.ationserver.dto.lounge.LoungeMemberStatusResponse;
import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.lounge.LoungeResponse;
import com.eureka.ationserver.dto.lounge.SocketLoungeStatusResponse;
import com.eureka.ationserver.dto.lounge.SocketMemberResponse;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.lounge.ELonugeStatus;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeMember;
import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import com.eureka.ationserver.model.lounge.LoungeTag;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
import com.eureka.ationserver.repository.lounge.LoungeChatRepository;
import com.eureka.ationserver.repository.lounge.LoungeMemberRepository;
import com.eureka.ationserver.repository.lounge.LoungeRepository;
import com.eureka.ationserver.repository.lounge.LoungeSubCategoryRepository;
import com.eureka.ationserver.repository.lounge.LoungeTagRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.repository.persona.SenseRepository;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class LoungeService {

  private final LoungeRepository loungeRepository;
  private final PersonaRepository personaRepository;
  private final MainCategoryRepository mainCategoryRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final SenseRepository senseRepository;
  private final LoungeSubCategoryRepository loungeSubCategoryRepository;
  private final LoungeTagRepository loungeTagRepository;
  private final LoungeMemberRepository loungeMemberRepository;
  private final LoungeChatRepository loungeChatRepository;
  private final UserRepository userRepository;
  private final SimpMessageSendingOperations messageSendingOperations;

  @Transactional
  public Long save(LoungeRequest loungeRequest) {

    String defaultPath = ImageUtil.getDefaultImagePath(Lounge.Lounge_PREFIX);

    Persona persona = personaRepository.getById(loungeRequest.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        loungeRequest.getMainCategoryId());

    Sense sense = senseRepository.getById(loungeRequest.getSenseId());

    Lounge saved = loungeRepository.save(
        loungeRequest.toEntity(persona, mainCategory, sense, defaultPath));

    saved.open();

    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        loungeRequest.getSubCategoryIdList());

    subCategoryList.stream().forEach(x -> loungeSubCategoryRepository.save(
        LoungeSubCategory.builder()
            .lounge(saved)
            .subCategory(x)
            .build()
    ));

    loungeRequest.getTagList().stream().forEach(x -> loungeTagRepository.save(
        LoungeTag.builder()
            .lounge(saved)
            .name(x)
            .build()
    ));

    loungeMemberRepository.save(
        LoungeMember.builder()
            .lounge(saved)
            .persona(persona)
            .admin(Boolean.TRUE)
            .ready(Boolean.TRUE)
            .userId(persona.getUser().getId())
            .build()
    );

    return saved.getId();

  }


  @Transactional(readOnly = true)
  public List<LoungeResponse> findAll() {
    return loungeRepository.findAll().stream().map(LoungeResponse::new)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public LoungeResponse find(Long loungeId) {
    return new LoungeResponse(loungeRepository.getById(loungeId));
  }

  @Transactional
  public Long update(Long loungeId, LoungeRequest loungeRequest) {

    Lounge lounge = loungeRepository.getById(loungeId);

    loungeSubCategoryRepository.deleteByLounge_Id(loungeId);

    loungeTagRepository.deleteByLounge_Id(loungeId);

    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        loungeRequest.getSubCategoryIdList());

    subCategoryList.stream().forEach(x -> loungeSubCategoryRepository.save(
        LoungeSubCategory.builder()
            .lounge(lounge)
            .subCategory(x)
            .build()
    ));

    loungeRequest.getTagList().stream().forEach(x -> loungeTagRepository.save(
        LoungeTag.builder()
            .lounge(lounge)
            .name(x)
            .build()
    ));

    Persona persona = personaRepository.getById(loungeRequest.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        loungeRequest.getMainCategoryId());

    Sense sense = senseRepository.getById(loungeRequest.getSenseId());

    lounge.update(loungeRequest, persona, mainCategory, sense);

    return loungeId;
  }

  @Transactional
  public Long delete(Long loungeId) {
    loungeRepository.deleteById(loungeId);
    return loungeId;
  }

  @Transactional
  public Long updateNotice(Long loungeId, String notice) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.setNotice(notice);
    return loungeId;
  }

  @Transactional
  public Long saveImg(Long loungeId, MultipartFile loungeImg) throws IOException {
    Lounge lounge = loungeRepository.getById(loungeId);
    List<String> pathList = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, loungeId);
    File file = new File(pathList.get(1));
    loungeImg.transferTo(file);
    lounge.setImgPath(pathList.get(0));
    return loungeId;
  }

  @Transactional
  public Long close(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.close();
    messageSendingOperations.convertAndSend(String.format("/lounge/%d/status/send", loungeId),
        SocketLoungeStatusResponse.builder().status(ELonugeStatus.END).build());
    return loungeId;
  }

  @Transactional
  public Long start(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.start();

    loungeMemberRepository.deleteByLounge_IdAndReady(loungeId, Boolean.FALSE);

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/status/send", loungeId),
        SocketLoungeStatusResponse.builder().status(ELonugeStatus.START).build());
    return loungeId;
  }

  @Transactional
  public Long enter(Long loungeId, Long personaId) {

    Persona persona = personaRepository.getById(personaId);

    if (loungeMemberRepository.findByLounge_IdAndUserId(loungeId, persona.getUser().getId())
        .isPresent()) {
      throw new CommonException("이미 입장 중인 유저 입니다.");
    }

    LoungeMember loungeMember = LoungeMember.builder()
        .lounge(loungeRepository.getById(loungeId))
        .persona(persona)
        .ready(Boolean.FALSE)
        .admin(Boolean.FALSE)
        .userId(persona.getUser().getId())
        .build();
    loungeMemberRepository.save(loungeMember);

    SocketMemberResponse socketMemberResponse = SocketMemberResponse.builder()
        .persona(new PersonaSimpleResponse(personaRepository.getById(personaId)))
        .status(EMemberStatus.ENTER)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        socketMemberResponse);

    return loungeId;
  }

  @Transactional
  public Long exit(Long loungeId, Long personaId) {
    loungeMemberRepository.deleteByLounge_IdAndPersona_Id(loungeId, personaId);

    SocketMemberResponse socketMemberResponse = SocketMemberResponse.builder()
        .persona(new PersonaSimpleResponse(personaRepository.getById(personaId)))
        .status(EMemberStatus.EXIT)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        socketMemberResponse);

    return loungeId;
  }

  @Transactional
  public Long ready(Long loungeId, Long personaId) {
    Persona persona = personaRepository.getById(personaId);

    LoungeMember loungeMember = loungeMemberRepository.findByLounge_IdAndUserId(loungeId,
        persona.getUser().getId()).get();

    loungeMember.setReady(Boolean.TRUE);

    SocketMemberResponse socketMemberResponse = SocketMemberResponse.builder()
        .persona(new PersonaSimpleResponse(personaRepository.getById(personaId)))
        .status(EMemberStatus.READY)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        socketMemberResponse);

    return loungeId;
  }

  @Transactional
  public Long unready(Long loungeId, Long personaId) {
    Persona persona = personaRepository.getById(personaId);

    LoungeMember loungeMember = loungeMemberRepository.findByLounge_IdAndUserId(loungeId,
        persona.getUser().getId()).get();

    loungeMember.setReady(Boolean.FALSE);

    SocketMemberResponse socketMemberResponse = SocketMemberResponse.builder()
        .persona(new PersonaSimpleResponse(personaRepository.getById(personaId)))
        .status(EMemberStatus.UNREADY)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        socketMemberResponse);

    return loungeId;
  }

  @Transactional
  public List<LoungeChatResponse> getChat(Long loungeId) {
    return loungeChatRepository.findByLounge_Id(loungeId).stream().map(LoungeChatResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional
  public List<LoungeMemberStatusResponse> getWait(UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return loungeMemberRepository.findByUserIdAndLounge_StatusAndReady(user.getId(), ELonugeStatus.OPEN,
            Boolean.TRUE).stream().map(
            LoungeMemberStatusResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional
  public List<LoungeMemberStatusResponse> getCurrent(UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return loungeMemberRepository.findByUserIdAndLounge_Status(user.getId(), ELonugeStatus.START).stream().map(
            LoungeMemberStatusResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional
  public List<LoungeMemberStatusResponse> getHistory(UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();

    return loungeMemberRepository.findByUserIdAndLounge_Status(user.getId(), ELonugeStatus.END).stream().map(
            LoungeMemberStatusResponse::new)
        .collect(
            Collectors.toList());
  }


}
