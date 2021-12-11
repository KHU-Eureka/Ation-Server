package com.eureka.ationserver.dto.pinBoard;

import com.eureka.ationserver.domain.insight.PinBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinBoardSimpleResponse {

    private Long id;

    private Long personaId;

    private String name;

    private String imgPath;



    public PinBoardSimpleResponse(PinBoard pinBoard){
        this.id = pinBoard.getId();
        this.personaId = pinBoard.getPersona().getId();
        this.name = pinBoard.getName();
        this.imgPath = pinBoard.getImgPath();
    }
}

