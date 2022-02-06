package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.model.lounge.LoungePin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungePinResponse {

  private LoungeResponse lounge;

  public LoungePinResponse(LoungePin loungePin){
    this.lounge = new LoungeResponse(loungePin.getLounge());
  }


}
