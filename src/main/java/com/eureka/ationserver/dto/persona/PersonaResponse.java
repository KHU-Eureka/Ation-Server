package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.dto.persona.category.SenseResponse;
import com.eureka.ationserver.model.persona.Persona;
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

    private Long id;

    private String nickname;

    private Integer age;

    private Integer gender;

    private String mbti;

    private String job;

    private String introduction;

    private String profileImgPath;

    private List<String> charmList;

    private List<Long> interestIdList;

    private List<Long> senseIdList;


  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleOut {

    private Long id;

    private String nickname;

    private String profileImgPath;

    private List<SenseResponse.Out> senseList;

    private String job;

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


}

