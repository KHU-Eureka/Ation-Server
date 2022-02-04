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
    this.id = persona.getId();
    this.nickname = persona.getNickname();
    this.profileImgPath = persona.getProfileImgPath();
  }

  public PersonaSimpleResponse(LoungeMember loungeMember) {

    List<SenseResponse> senseList = new ArrayList<>();
    loungeMember.getPersona().getPersonaSenseList().stream()
        .forEach(x -> senseList.add(new SenseResponse(x.getSense())));

    this.id = loungeMember.getPersona().getId();
    this.nickname = loungeMember.getPersona().getNickname();
    this.profileImgPath = loungeMember.getPersona().getProfileImgPath();
    this.senseList = senseList;

  }
}
