package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.model.persona.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaResponse {

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

    public PersonaResponse(Persona persona){

        List<String> charmList = new ArrayList<>();
        persona.getPersonaCharmList().stream().forEach(x -> charmList.add(x.getName()));

        List<Long> interestIdList = new ArrayList<>();
        List<Long> senseIdList = new ArrayList<>();
        persona.getPersonaSenseList().stream().forEach(x->senseIdList.add(x.getSense().getId()));
        persona.getPersonaInterestList().stream().forEach(x->interestIdList.add(x.getInterest().getId()));

        this.id = persona.getId();
        this.nickname = persona.getNickname();
        this.age = persona.getAge();
        this.mbti = persona.getMbti();
        this.gender = persona.getGender();
        this.job = persona.getJob();
        this.introduction = persona.getIntroduction();
        this.profileImgPath = persona.getProfileImgPath();
        this.charmList = charmList;
        this.senseIdList = senseIdList;
        this.interestIdList = interestIdList;


    }

}
