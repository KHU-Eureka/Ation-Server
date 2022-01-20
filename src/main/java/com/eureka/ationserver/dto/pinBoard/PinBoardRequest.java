package com.eureka.ationserver.dto.pinBoard;

import com.eureka.ationserver.model.insight.PinBoard;
import com.eureka.ationserver.model.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinBoardRequest {

    @NotEmpty
    private Long personaId;

    @NotEmpty
    private String name;

    public PinBoard toEntity(Persona persona, String imgPath){
        return PinBoard.builder()
                        .persona(persona)
                        .imgPath(imgPath)
                        .name(name)
                        .build();
    }

}
