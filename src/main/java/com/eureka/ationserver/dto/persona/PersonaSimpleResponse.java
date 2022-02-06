package com.eureka.ationserver.dto.persona;

import com.eureka.ationserver.dto.sense.SenseResponse;
import com.eureka.ationserver.model.lounge.LoungeMember;
import com.eureka.ationserver.model.persona.Persona;
import java.util.ArrayList;
import java.util.List;
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

  private List<SenseResponse> senseList;

  public PersonaSimpleResponse(Persona persona) {

    List<SenseResponse> senseList = new ArrayList<>();
    persona.getPersonaSenseList().stream()
        .forEach(x -> senseList.add(new SenseResponse(x)));

    this.id = persona.getId();
    this.nickname = persona.getNickname();
    this.profileImgPath = persona.getProfileImgPath();
    this.senseList = senseList;
  }

}
