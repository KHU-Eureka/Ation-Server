package com.eureka.sensationserver.dto.persona;

import com.eureka.sensationserver.domain.persona.Persona;

import java.util.List;

public class PersonaResponse {
    private String name;

    private Integer age;

    private String gender;

    private String mbti;

    private List<String> charmList;

    private List<Long> senseIdList;
    private List<String> senseList;


    private List<Long> interestIdList;
    private List<String> interestList;


    private List<Long> jobIdList;
    private List<String> jobList;

    public PersonaResponse(Persona persona){
        this.name = persona.getName();
        this.age = persona.getAge();
        this.mbti = persona.getMbti();
        this.gender = persona.getGender();

    }

}
