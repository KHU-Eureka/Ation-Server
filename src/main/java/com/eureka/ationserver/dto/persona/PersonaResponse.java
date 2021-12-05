package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.domain.persona.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private String profileImgPath;

    private List<String> charmList;

    private List<InterestResponse> interestList;

    private List<SenseResponse> senseList;

    public PersonaResponse(Persona persona){

        List<String> charmList = new ArrayList<>();
        persona.getPersonaCharmList().stream().forEach(x -> charmList.add(x.getName()));

        List<SenseResponse> senseResponseList = persona.getPersonaSenseList().stream().map(SenseResponse::new).collect(Collectors.toList());

        List<InterestResponse> interestResponseList = persona.getPersonaInterestList().stream().map(InterestResponse::new).collect(Collectors.toList());

        this.id = persona.getId();
        this.nickname = persona.getNickname();
        this.age = persona.getAge();
        this.mbti = persona.getMbti();
        this.gender = persona.getGender();
        this.job = persona.getJob();
        this.profileImgPath = persona.getProfileImgPath();
        this.charmList = charmList;
        this.senseList = senseResponseList;
        this.interestList = interestResponseList;

    }

}
