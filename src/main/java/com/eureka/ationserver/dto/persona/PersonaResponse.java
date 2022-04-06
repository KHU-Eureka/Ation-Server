package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.dto.persona.category.SenseResponse;
import com.eureka.ationserver.model.persona.Persona;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PersonaResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "페르소나 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "페르소나 닉네임", position = 1)
    private String nickname;

    @ApiModelProperty(value = "페르소나 나이", position = 2)
    private Integer age;

    @ApiModelProperty(value = "페르소나 성별", position = 3)
    private Integer gender;

    @ApiModelProperty(value = "페르소나 mbti", position = 4)
    private String mbti;

    @ApiModelProperty(value = "페르소나 직업", position = 5)
    private String job;

    @ApiModelProperty(value = "페르소나 소개", position = 6)
    private String introduction;

    @ApiModelProperty(value = "페르소나 프로필이미지", position = 7)
    private String profileImgPath;

    @ApiModelProperty(value = "페르소나 매력", position = 8)
    private List<String> charmList;

    @ApiModelProperty(value = "페르소나 분야", position = 9)
    private List<Long> interestIdList;

    @ApiModelProperty(value = "페르소나 발달감각", position = 9)
    private List<Long> senseIdList;


  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleOut {

    @ApiModelProperty(value = "페르소나 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "페르소나 닉네임", position = 1)
    private String nickname;

    @ApiModelProperty(value = "페르소나 직업", position = 2)
    private String job;

    @ApiModelProperty(value = "페르소나 프로필이미지", position = 3)
    private String profileImgPath;

    @ApiModelProperty(value = "페르소나 발달감각", position = 4)
    private List<SenseResponse.Out> senseList;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class IdOut {

    @ApiModelProperty(value = "페르소나 id", position = 0)
    private Long id;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class DuplicateOut {

    @ApiModelProperty(value = "중복여부", position = 0)
    private Boolean duplicate;

  }

  public static PersonaResponse.Out toOut(Persona persona) {

    if (persona == null) {
      return null;
    }
    Out out = new Out();

    List<String> charmList = new ArrayList<>();
    List<Long> interestIdList = new ArrayList<>();
    List<Long> senseIdList = new ArrayList<>();

    persona.getPersonaCharmList().forEach(x -> charmList.add(x.getName()));
    persona.getPersonaSenseList().forEach(x -> senseIdList.add(x.getSense().getId()));
    persona.getPersonaInterestList().forEach(x -> interestIdList.add(x.getInterest().getId()));

    out.id = persona.getId();
    out.nickname = persona.getNickname();
    out.age = persona.getAge();
    out.mbti = persona.getMbti();
    out.gender = persona.getGender();
    out.job = persona.getJob();
    out.introduction = persona.getIntroduction();
    out.profileImgPath = persona.getProfileImgPath();
    out.charmList = charmList;
    out.senseIdList = senseIdList;
    out.interestIdList = interestIdList;

    return out;

  }

  public static PersonaResponse.SimpleOut toSimpleOut(Persona persona) {

    if (persona == null) {
      return null;
    }

    SimpleOut simpleOut = new SimpleOut();

    List<SenseResponse.Out> senseList = new ArrayList<>();
    persona.getPersonaSenseList().forEach(x -> senseList.add(SenseResponse.toOut(x)));

    simpleOut.id = persona.getId();
    simpleOut.nickname = persona.getNickname();
    simpleOut.profileImgPath = persona.getProfileImgPath();
    simpleOut.senseList = senseList;
    simpleOut.job = persona.getJob();

    return simpleOut;
  }

  public static PersonaResponse.IdOut toIdOut(Long id) {

    if (id == null) {
      return null;
    }

    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;

  }

  public static PersonaResponse.DuplicateOut toDuplicateOut(Boolean duplicate) {

    if (duplicate == null) {
      return null;
    }

    DuplicateOut duplicateOut = new DuplicateOut();
    duplicateOut.duplicate = duplicate;
    return duplicateOut;

  }
}

