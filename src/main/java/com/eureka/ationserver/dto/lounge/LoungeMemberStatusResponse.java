package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.lounge.LoungeMember;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoungeMemberStatusResponse {

  private LoungeResponse lounge;

  private PersonaResponse.SimpleOut persona;

  private Boolean ready;

  private Boolean admin;

  public LoungeMemberStatusResponse(LoungeMember loungeMember){
    this.lounge = new LoungeResponse(loungeMember.getLounge());
    this.persona = PersonaResponse.toSimpleOut(loungeMember.getPersona());
    this.ready = loungeMember.getReady();
    this.admin = loungeMember.getAdmin();
  }



}
