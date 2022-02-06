package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.model.lounge.LoungeMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeMemberStatusResponse {

  private LoungeResponse lounge;

  private PersonaSimpleResponse persona;

  private Boolean ready;

  private Boolean admin;

  public LoungeMemberStatusResponse(LoungeMember loungeMember){
    this.lounge = new LoungeResponse(loungeMember.getLounge());
    this.persona = new PersonaSimpleResponse(loungeMember.getPersona());
    this.ready = loungeMember.getReady();
    this.admin = loungeMember.getAdmin();
  }



}
