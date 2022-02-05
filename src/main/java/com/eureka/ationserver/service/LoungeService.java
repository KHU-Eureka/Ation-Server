package com.eureka.ationserver.service;

import com.eureka.ationserver.advice.exception.CommonException;
import com.eureka.ationserver.dto.lounge.LoungeChatResponse;
import com.eureka.ationserver.dto.lounge.LoungeMemberResponse;
import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.lounge.LoungeResponse;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeMember;
import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import com.eureka.ationserver.model.lounge.LoungeTag;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
import com.eureka.ationserver.repository.lounge.LoungeChatRepository;
import com.eureka.ationserver.repository.lounge.LoungeMemberRepository;
import com.eureka.ationserver.repository.lounge.LoungeRepository;
import com.eureka.ationserver.repository.lounge.LoungeSubCategoryRepository;
import com.eureka.ationserver.repository.lounge.LoungeTagRepository;
import com.eureka.ationserver.repository.persona.PersonaRepository;
import com.eureka.ationserver.repository.persona.SenseRepository;
import com.eureka.ationserver.utils.image.ImageUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
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
  private final LoungeTagRepository loungeTagRepository;
  private final LoungeMemberRepository loungeMemberRepository;
  private final LoungeChatRepository loungeChatRepository;

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
  public Long close(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.close();
    return loungeId;
  }

  @Transactional
  public Long start(Long loungeId) {
    Lounge lounge = loungeRepository.getById(loungeId);
    lounge.start();
    return loungeId;
  }

  @Transactional
  public Long enter(Long loungeId, Long personaId) {
    if (loungeMemberRepository.findByLounge_IdAndPersona_Id(loungeId, personaId).isPresent()) {
      throw new CommonException("이미 입장 중인 페르소나 입니다.");
    }

    LoungeMember loungeMember = LoungeMember.builder()
        .lounge(loungeRepository.getById(loungeId))
        .persona(personaRepository.getById(personaId))
        .ready(Boolean.FALSE)
        .build();

    // TODO socket

    return loungeId;
  }

  @Transactional
  public Long exit(Long loungeId, Long personaId) {
    loungeMemberRepository.deleteByLounge_IdAndPersona_Id(loungeId, personaId);

    // TODO socket

    return loungeId;
  }

  @Transactional
  public List<LoungeChatResponse> getChat(Long loungeId){
    return loungeChatRepository.findByLounge_Id(loungeId).stream().map(LoungeChatResponse::new).collect(
        Collectors.toList());
  }


}
