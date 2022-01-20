package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.model.persona.Persona;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonaSimpleResponse {
    private Long id;

    private String nickname;

    private String profileImgPath;

    public PersonaSimpleResponse(Persona persona){
        this.id = persona.getId();
        this.nickname = persona.getNickname();
        this.profileImgPath = persona.getProfileImgPath();
    }
}
