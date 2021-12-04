package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.persona.Persona;
import com.eureka.ationserver.domain.user.User;
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

    public PinBoard toEntity(User user, Persona persona, String imgPath){
        return PinBoard.builder()
                        .user(user)
                        .persona(persona)
                        .imgPath(imgPath)
                        .name(name)
                        .build();
    }

}
