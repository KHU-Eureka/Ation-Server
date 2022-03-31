package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.lounge.LoungeChat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeChatResponse {

  private PersonaResponse.SimpleOut persona;

  private String content;

  private LocalDateTime createdAt;

  public LoungeChatResponse(LoungeChat loungeChat){
    this.persona = PersonaResponse.toSimpleOut(loungeChat.getPersona());
    this.content = loungeChat.getContent();
    this.createdAt = loungeChat.getCreatedAt();
  }

}
