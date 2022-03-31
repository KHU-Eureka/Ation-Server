package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.lounge.LoungeMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeMemberResponse {

  private Boolean ready;

  private Boolean admin;

  private PersonaResponse.SimpleOut persona;

  public LoungeMemberResponse(LoungeMember loungeMember) {
    this.ready = loungeMember.getReady();
    this.admin = loungeMember.getAdmin();
    this.persona = PersonaResponse.toSimpleOut(loungeMember.getPersona());
  }

}
