package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.CommonException;
import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.lounge.LoungeResponse;
import com.eureka.ationserver.dto.lounge.status.LoungeMemberStatus;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.dto.socket.LoungeSocketDto;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeMember;
import com.eureka.ationserver.model.lounge.LoungePin;
import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import com.eureka.ationserver.model.lounge.status.LoungeStatus;
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
import com.eureka.ationserver.utils.image.ImageUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
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
  private final SimpMessageSendingOperations messageSendingOperations;
  private final LoungePinRepository loungePinRepository;
  private final LoungeImageRepository loungeImageRepository;
  private final UserService userService;

  @Transactional
  public LoungeResponse.IdOut save(LoungeRequest.In in) {

    Persona persona = personaRepository.getById(in.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        in.getMainCategoryId());

    Sense sense = senseRepository.getById(in.getSenseId());

    String imgPath = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, in.getImageId())
        .get(0);

    Lounge saved = loungeRepository.save(
        in.toLounge(persona, mainCategory, sense, imgPath));

    saved.open();

    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        in.getSubCategoryIdList());

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

    return LoungeResponse.toIdOut(saved.getId());

  }


  @Transactional(readOnly = true)
  public List<LoungeResponse.Out> findAll() {
    return loungeRepository.findAll(Sort.by(Direction.DESC, "createdAt")).stream()
        .map(LoungeResponse::toOut)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public LoungeResponse.Out find(Long loungeId) {
    return LoungeResponse.toOut(loungeRepository.getById(loungeId));
  }

  @Transactional
  public LoungeResponse.IdOut update(Long loungeId, LoungeRequest.In in) {

    Lounge lounge = loungeRepository.getById(loungeId);

    loungeSubCategoryRepository.deleteByLounge_Id(loungeId);

    List<SubCategory> subCategoryList = subCategoryRepository.findAllByIdIn(
        in.getSubCategoryIdList());

    String imgPath = ImageUtil.getImagePath(Lounge.Lounge_PREFIX, in.getImageId())
        .get(0);

    subCategoryList.stream().forEach(x -> loungeSubCategoryRepository.save(
        LoungeSubCategory.builder()
            .lounge(lounge)
            .subCategory(x)
            .build()
    ));

    Persona persona = personaRepository.getById(in.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        in.getMainCategoryId());

    Sense sense = senseRepository.getById(in.getSenseId());

    lounge.update(in, persona, mainCategory, sense, imgPath);

    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut delete(Long loungeId) {
    loungeRepository.deleteById(loungeId);
    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut updateNotice(Long loungeId, LoungeRequest.NoticeIn in) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.setNotice(in.getNotice());
    messageSendingOperations.convertAndSend(String.format("/lounge/%d/notice/send", loungeId),
        LoungeResponse.toNoticeOut(in.getNotice()));
    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.WhiteboardOut updateWhiteboard(Long loungeId,
      LoungeRequest.WhiteboardIn in) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.setWhiteboard(in.getWhiteboard());
    return LoungeResponse.toWhiteboardOut(in.getWhiteboard());
  }

  @Transactional
  public LoungeResponse.IdOut end(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.end();
    messageSendingOperations.convertAndSend(String.format("/lounge/%d/status/send", loungeId),
        LoungeSocketDto.Status.builder().loungeId(loungeId).status(LoungeStatus.END)
            .build());
    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut start(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.start();

    loungeMemberRepository.deleteByLounge_IdAndReady(loungeId, Boolean.FALSE);
    loungeChatRepository.deleteByLounge_Id(loungeId);

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/status/send", loungeId),
        LoungeSocketDto.Status.builder().loungeId(loungeId).status(LoungeStatus.START)
            .build());
    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut enter(Long loungeId, Long personaId) {

    Persona persona = personaRepository.getById(personaId);
    Lounge lounge = loungeRepository.getById(loungeId);
    if (lounge.getLimitMember() != 0) {
      if (lounge.getLoungeMemberList().size() >= lounge.getLimitMember()) {
        throw new CommonException("인원이 다 찼습니다.");
      }
    }

    if (loungeMemberRepository.findByLounge_IdAndUserId(loungeId, persona.getUser().getId())
        .isPresent()) {
      return LoungeResponse.toIdOut(loungeId);
    }

    LoungeMember loungeMember = LoungeMember.builder()
        .lounge(lounge)
        .persona(persona)
        .ready(Boolean.FALSE)
        .admin(Boolean.FALSE)
        .userId(persona.getUser().getId())
        .build();
    loungeMemberRepository.save(loungeMember);

    LoungeSocketDto.Member memberOut = LoungeSocketDto.Member.builder()
        .persona(PersonaResponse.toSimpleOut(personaRepository.getById(personaId)))
        .status(LoungeMemberStatus.ENTER)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        memberOut);

    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut exit(Long loungeId, Long personaId) {
    loungeMemberRepository.deleteByLounge_IdAndPersona_Id(loungeId, personaId);

    LoungeSocketDto.Member memberOut = LoungeSocketDto.Member.builder()
        .persona(PersonaResponse.toSimpleOut(personaRepository.getById(personaId)))
        .status(LoungeMemberStatus.EXIT)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        memberOut);

    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut ready(Long loungeId, Long personaId) {
    Persona persona = personaRepository.getById(personaId);

    if (loungeMemberRepository.findByUserIdAndLounge_StatusAndReady(persona.getUser().getId(),
        LoungeStatus.OPEN, Boolean.TRUE).size() >= 3) {
      throw new CommonException("대기 중인 라운지가 3개 입니다.");
    }

    LoungeMember loungeMember = loungeMemberRepository.findByLounge_IdAndUserId(loungeId,
        persona.getUser().getId()).get();

    loungeMember.setReady(Boolean.TRUE);

    LoungeSocketDto.Member memberOut = LoungeSocketDto.Member.builder()
        .persona(PersonaResponse.toSimpleOut(personaRepository.getById(personaId)))
        .status(LoungeMemberStatus.READY)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        memberOut);

    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut unready(Long loungeId, Long personaId) {
    Persona persona = personaRepository.getById(personaId);

    LoungeMember loungeMember = loungeMemberRepository.findByLounge_IdAndUserId(loungeId,
        persona.getUser().getId()).get();

    loungeMember.setReady(Boolean.FALSE);

    LoungeSocketDto.Member memberOut = LoungeSocketDto.Member.builder()
        .persona(PersonaResponse.toSimpleOut(personaRepository.getById(personaId)))
        .status(LoungeMemberStatus.UNREADY)
        .build();

    messageSendingOperations.convertAndSend(String.format("/lounge/%d/member/send", loungeId),
        memberOut);

    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public List<LoungeResponse.ChatOut> getChat(Long loungeId) {
    return loungeChatRepository.findByLounge_Id(loungeId).stream().map(LoungeResponse::toChatOut)
        .collect(Collectors.toList());
  }

  @Transactional
  public List<LoungeResponse.MemberStatusOut> getWait() {
    User user = userService.auth();
    return loungeMemberRepository.findByUserIdAndLounge_StatusAndReady(user.getId(),
        LoungeStatus.OPEN,
        Boolean.TRUE).stream().map(
        LoungeResponse::toMemberStatusOut).collect(Collectors.toList());
  }

  @Transactional
  public List<LoungeResponse.MemberStatusOut> getCurrent() {
    User user = userService.auth();
    return loungeMemberRepository.findByUserIdAndLounge_Status(user.getId(), LoungeStatus.START)
        .stream().map(LoungeResponse::toMemberStatusOut).collect(Collectors.toList());
  }

  @Transactional
  public List<LoungeResponse.MemberStatusOut> getHistory() {
    User user = userService.auth();
    return loungeMemberRepository.findByUserIdAndLounge_Status(user.getId(), LoungeStatus.END)
        .stream().map(LoungeResponse::toMemberStatusOut).collect(Collectors.toList());
  }

  @Transactional
  public LoungeResponse.IdOut deleteHistory(Long loungeId) {
    User user = userService.auth();
    loungeMemberRepository.deleteByLounge_IdAndUserId(loungeId, user.getId());
    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut pin(Long loungeId) {
    User user = userService.auth();
    if (loungePinRepository.findByLounge_IdAndUserId(loungeId, user.getId()).isPresent()) {
      throw new CommonException("이미 핀 한 라운지 입니다.");
    }

    loungePinRepository.save(LoungePin.builder()
        .lounge(loungeRepository.getById(loungeId))
        .userId(user.getId())
        .build());

    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional
  public LoungeResponse.IdOut deletePin(Long loungeId) {
    User user = userService.auth();
    loungePinRepository.deleteByLounge_IdAndUserId(loungeId, user.getId());
    return LoungeResponse.toIdOut(loungeId);
  }

  @Transactional(readOnly = true)
  public List<LoungeResponse.PinOut> getPin() {
    User user = userService.auth();
    return loungePinRepository.findByUserId(user.getId()).stream().map(LoungeResponse::toPinOut)
        .collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public List<LoungeResponse.ImageOut> getImage() {
    return loungeImageRepository.findAll().stream().map(LoungeResponse::toImageOut)
        .collect(Collectors.toList());
  }


}
