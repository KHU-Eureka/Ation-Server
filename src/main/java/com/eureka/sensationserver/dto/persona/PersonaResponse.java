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

    private List<PersonaInterest> personaInterestList;

    private List<PersonaJob> personaJobList;

    private List<SenseResponse> senseList;

    public PersonaResponse(Persona persona, List<SenseResponse> senseResponseList, List<String> charmList){
        this.name = persona.getName();
        this.age = persona.getAge();
        this.mbti = persona.getMbti();
        this.gender = persona.getGender();
        this.senseList = senseResponseList;
        this.charmList = charmList;
//        this.personaCharmList = persona.getPersonaCharmList();
//        this.personaInterestList = persona.getPersonaInterestList();
//        this.personaJobList = persona.getPersonaJobList();
//        this.personaSenseList = persona.getPersonaSenseList();

    }

}
