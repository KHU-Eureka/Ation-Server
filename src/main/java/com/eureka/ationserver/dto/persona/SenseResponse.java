package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.domain.persona.PersonaSense;
import com.eureka.ationserver.domain.persona.Sense;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SenseResponse {
    private Long senseId;
    private String name;

    public SenseResponse(PersonaSense personaSense){
        senseId = personaSense.getSense().getId();
        name = personaSense.getSense().getName();
    }

    public SenseResponse(Sense sense){
        senseId = sense.getId();
        name = sense.getName();
    }
}
