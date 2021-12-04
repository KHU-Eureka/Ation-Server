package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinBoardResponse {

    private PersonaSimpleResponse personaSimpleResponse;

    private String name;

    private String imgPath;


    public PinBoardResponse(PinBoard pinBoard, PersonaSimpleResponse personaSimpleResponse){
        this.personaSimpleResponse = personaSimpleResponse;
        this.name = pinBoard.getName();
        this.imgPath = pinBoard.getImgPath();
    }
}
