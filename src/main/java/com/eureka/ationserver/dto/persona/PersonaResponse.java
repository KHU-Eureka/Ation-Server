package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.domain.persona.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String profileImgPath;

    private List<String> charmList;

    private List<InterestResponse> interestList;

    private List<SenseResponse> senseList;

    public PersonaResponse(Persona persona, List<String> charmList, List<SenseResponse> senseResponseList, List<InterestResponse> interestResponseList){
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
