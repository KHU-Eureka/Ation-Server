package com.eureka.ationserver.dto.pinBoard;

import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinBoardResponse {
    private Long id;

    private PersonaSimpleResponse persona;

    private String name;

    private String imgPath;




    public PinBoardResponse(PinBoard pinBoard){
        this.id = pinBoard.getId();
        this.persona = new PersonaSimpleResponse(pinBoard.getPersona());
        this.name = pinBoard.getName();
        this.imgPath = pinBoard.getImgPath();
    }
}
