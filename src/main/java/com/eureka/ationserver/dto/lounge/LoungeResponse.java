package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.category.MainCategoryResponse;
import com.eureka.ationserver.dto.category.SubCategoryResponse;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.dto.sense.SenseResponse;
import com.eureka.ationserver.model.lounge.ELonugeStatus;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.repository.lounge.LoungeMemberRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeResponse {

  private Long id;

  private String title;

  private Integer limitMember;

  private ELonugeStatus status; // 0 : 모집 중, 1 : 종료, 2: 진행 중

  private String introduction;

  private String notice;

  private String imgPath;

  private Long totalMember;

  private LocalDateTime createdAt;

  private PersonaSimpleResponse persona;

  private SenseResponse sense;

  private MainCategoryResponse mainCategory;

  private List<SubCategoryResponse> subCategoryList;

  private List<String> tagList;

  private List<LoungeMemberResponse> memberList;

  public LoungeResponse(Lounge lounge) {

    MainCategoryResponse mainCategoryResponse = new MainCategoryResponse(
        lounge.getLoungeMainCategory());
    List<SubCategoryResponse> subCategoryResponseList = new ArrayList<>();
    lounge.getLoungeSubCategoryList().stream()
        .forEach(x -> subCategoryResponseList.add(new SubCategoryResponse(x)));
    List<String> tagList = new ArrayList<>();
    lounge.getLoungeTagList().stream().forEach(x -> tagList.add(x.getName()));
    PersonaSimpleResponse personaSimpleResponse = new PersonaSimpleResponse(lounge.getPersona());
    List<LoungeMemberResponse> memberList = new ArrayList<>();
    lounge.getLoungeMemberList().stream()
        .forEach(x -> memberList.add(new LoungeMemberResponse(x)));

    this.id = lounge.getId();
    this.title = lounge.getTitle();
    this.limitMember = lounge.getLimitMember();
    this.status = lounge.getStatus();
    this.introduction = lounge.getIntroduction();
    this.notice = lounge.getNotice();
    this.imgPath = lounge.getImgPath();
    this.createdAt = lounge.getCreatedAt();
    this.sense = new SenseResponse(lounge.getSense());
    this.persona = personaSimpleResponse;
    this.mainCategory = mainCategoryResponse;
    this.subCategoryList = subCategoryResponseList;
    this.tagList = tagList;
    this.memberList = memberList;

  }

}
