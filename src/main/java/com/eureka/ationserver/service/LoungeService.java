package com.eureka.ationserver.service;

import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.lounge.LoungeResponse;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.category.SubCategory;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import com.eureka.ationserver.model.lounge.LoungeTag;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import com.eureka.ationserver.repository.category.MainCategoryRepository;
import com.eureka.ationserver.repository.category.SubCategoryRepository;
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

  @Transactional
  public Long save(LoungeRequest loungeRequest) {

    String defaultPath = ImageUtil.getDefaultImagePath(Lounge.Lounge_PREFIX);

    Persona persona = personaRepository.getById(loungeRequest.getPersonaId());

    MainCategory mainCategory = mainCategoryRepository.getById(
        loungeRequest.getMainCategoryId());

    Sense sense = senseRepository.getById(loungeRequest.getSenseId());

    Lounge saved = loungeRepository.save(
        loungeRequest.toEntity(persona, mainCategory, sense, defaultPath));

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
  public List<LoungeResponse> findAll(){
    return loungeRepository.findAll().stream().map(LoungeResponse::new).collect(Collectors.toList());
  }

  @Transactional(readOnly = true)
  public LoungeResponse find(Long loungeId){
    return new LoungeResponse(loungeRepository.getById(loungeId));
  }

}
