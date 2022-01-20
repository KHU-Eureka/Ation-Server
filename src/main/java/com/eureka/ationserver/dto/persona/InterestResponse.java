package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.model.persona.Interest;
import com.eureka.ationserver.model.persona.PersonaInterest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InterestResponse {
    private Long interestId;
    private String name;

    public InterestResponse(PersonaInterest personaInterest){
        interestId = personaInterest.getInterest().getId();
        name = personaInterest.getInterest().getName();
    }

    public InterestResponse(Interest interest){
        interestId = interest.getId();
        name = interest.getName();
    }
}
