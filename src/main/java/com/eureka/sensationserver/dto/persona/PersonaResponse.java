package com.eureka.sensationserver.dto.persona;

import com.eureka.sensationserver.domain.persona.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaResponse {
    private String name;

    private Integer age;

    private String gender;

    private String mbti;

    private List<String> charmList;

    private List<InterestResponse> interestList;

    private List<JobResponse> jobList;

    private List<SenseResponse> senseList;

    public PersonaResponse(Persona persona, List<String> charmList, List<SenseResponse> senseResponseList, List<JobResponse> jobResponseList, List<InterestResponse> interestResponseList){
        this.name = persona.getName();
        this.age = persona.getAge();
        this.mbti = persona.getMbti();
        this.gender = persona.getGender();
        this.charmList = charmList;
        this.senseList = senseResponseList;
        this.jobList = jobResponseList;
        this.interestList = interestResponseList;

    }

}
