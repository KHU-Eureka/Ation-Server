package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.model.lounge.LoungeMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeMemberResponse {

  private Boolean ready;

  private PersonaSimpleResponse persona;

  public LoungeMemberResponse(LoungeMember loungeMember) {
    this.ready = loungeMember.getReady();
    this.persona = new PersonaSimpleResponse(loungeMember);
  }

}
