package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.CommonException;
import com.eureka.ationserver.dto.lounge.EMemberStatus;
import com.eureka.ationserver.dto.lounge.LoungeChatResponse;
import com.eureka.ationserver.dto.lounge.LoungeImageResponse;
import com.eureka.ationserver.dto.lounge.LoungeMemberStatusResponse;
import com.eureka.ationserver.dto.lounge.LoungePinResponse;
import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.lounge.LoungeResponse;
import com.eureka.ationserver.dto.lounge.SocketLoungeStatusResponse;
import com.eureka.ationserver.dto.lounge.SocketMemberResponse;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.lounge.ELoungeStatus;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeMember;
import com.eureka.ationserver.model.lounge.LoungePin;
import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
import com.eureka.ationserver.repository.lounge.LoungeChatRepository;
import com.eureka.ationserver.repository.lounge.LoungeImageRepository;
import com.eureka.ationserver.repository.lounge.LoungeMemberRepository;
import com.eureka.ationserver.repository.lounge.LoungePinRepository;
import com.eureka.ationserver.repository.lounge.LoungeRepository;
import com.eureka.ationserver.repository.lounge.LoungeSubCategoryRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.repository.persona.SenseRepository;
import com.eureka.ationserver.repository.user.UserRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoungeService {

  private final LoungeRepository loungeRepository;
  private final PersonaRepository personaRepository;
  private final MainCategoryRepository mainCategoryRepository;
  private final SubCategoryRepository subCategoryRepository;
  private final SenseRepository senseRepository;
  private final LoungeSubCategoryRepository loungeSubCategoryRepository;
  private final LoungeMemberRepository loungeMemberRepository;
  private final LoungeChatRepository loungeChatRepository;
  private final UserRepository userRepository;
  private final SimpMessageSendingOperations messageSendingOperations;
  private final LoungePinRepository loungePinRepository;
  private final LoungeImageRepository loungeImageRepository;

  @Transactional
  public Long save(LoungeRequest loungeRequest) {

    Persona persona = personaRepository.getById(loungeRequest.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        loungeRequest.getMainCategoryId());

    Sense sense = senseRepository.getById(loungeRequest.getSenseId());

    String imgPath = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, loungeRequest.getImageId())
        .get(0);

    Lounge saved = loungeRepository.save(
        loungeRequest.toEntity(persona, mainCategory, sense, imgPath));

    saved.open();

    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        loungeRequest.getSubCategoryIdList());

    subCategoryList.stream().forEach(x -> loungeSubCategoryRepository.save(
        LoungeSubCategory.builder()
            .lounge(saved)
            .subCategory(x)
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

    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        loungeRequest.getSubCategoryIdList());

    String imgPath = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, loungeRequest.getImageId())
        .get(0);

    subCategoryList.stream().forEach(x -> loungeSubCategoryRepository.save(
        LoungeSubCategory.builder()
            .lounge(lounge)
            .subCategory(x)
            .build()
    ));

    Persona persona = personaRepository.getById(loungeRequest.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        loungeRequest.getMainCategoryId());

    Sense sense = senseRepository.getById(loungeRequest.getSenseId());

    lounge.update(loungeRequest, persona, mainCategory, sense, imgPath);

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
  public Long end(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.end();
    messageSendingOperations.convertAndSend(String.format("/lounge/%d/status/send", loungeId),
        SocketLoungeStatusResponse.builder().status(ELoungeStatus.END).build());
    return loungeId;
  }

  @Transactional
  public Long start(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.start();

    loungeMemberRepository.deleteByLounge_IdAndReady(loungeId, Boolean.FALSE);
    loungeChatRepository.deleteByLounge_Id(loungeId);

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/status/send", loungeId),
        SocketLoungeStatusResponse.builder().status(ELoungeStatus.START).build());
    return loungeId;
  }

  @Transactional
  public Long enter(Long loungeId, Long personaId) {

    Persona persona = personaRepository.getById(personaId);
    Lounge lounge = loungeRepository.getById(loungeId);
    if (lounge.getLoungeMemberList().size() >= lounge.getLimitMember()) {
      throw new CommonException("인원이 다 찼습니다.");
    }

    if (loungeMemberRepository.findByLounge_IdAndUserId(loungeId, persona.getUser().getId())
        .isPresent()) {
      throw new CommonException("이미 입장 중인 유저 입니다.");
    }

    LoungeMember loungeMember = LoungeMember.builder()
        .lounge(lounge)
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

    if (loungeMemberRepository.findByUserIdAndLounge_StatusAndReady(persona.getUser().getId(),
        ELoungeStatus.OPEN, Boolean.TRUE).size() >= 3) {
      throw new CommonException("대기 중인 라운지가 3개 입니다.");
    }

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
    return loungeMemberRepository.findByUserIdAndLounge_StatusAndReady(user.getId(),
            ELoungeStatus.OPEN,
            Boolean.TRUE).stream().map(
            LoungeMemberStatusResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional
  public List<LoungeMemberStatusResponse> getCurrent(UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();
    return loungeMemberRepository.findByUserIdAndLounge_Status(user.getId(), ELoungeStatus.START)
        .stream().map(
            LoungeMemberStatusResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional
  public List<LoungeMemberStatusResponse> getHistory(UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();

    return loungeMemberRepository.findByUserIdAndLounge_Status(user.getId(), ELoungeStatus.END)
        .stream().map(
            LoungeMemberStatusResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional
  public Long pin(UserDetails userDetails, Long loungeId) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();

    if (loungePinRepository.findByLounge_IdAndUserId(loungeId, user.getId()).isPresent()) {
      throw new CommonException("이미 핀 한 라운지 입니다.");
    }

    loungePinRepository.save(LoungePin.builder()
        .lounge(loungeRepository.getById(loungeId))
        .userId(user.getId())
        .build());

    return loungeId;
  }

  @Transactional
  public Long deletePin(UserDetails userDetails, Long lougeId) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();

    loungePinRepository.deleteByLounge_IdAndUserId(lougeId, user.getId());
    return lougeId;
  }

  @Transactional(readOnly = true)
  public List<LoungePinResponse> getPin(UserDetails userDetails) {
    User user = userRepository.findByEmail(userDetails.getUsername()).get();

    return loungePinRepository.findByUserId(user.getId()).stream().map(LoungePinResponse::new)
        .collect(
            Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<LoungeImageResponse> getImage() {
    return loungeImageRepository.findAll().stream().map(LoungeImageResponse::new)
        .collect(Collectors.toList());
  }


}
