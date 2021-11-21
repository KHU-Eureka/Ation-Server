package com.eureka.sensationserver.dto.persona;

import com.eureka.sensationserver.domain.persona.PersonaSense;
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
}
