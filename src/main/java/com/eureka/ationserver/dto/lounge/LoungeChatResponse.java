package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import com.eureka.ationserver.model.lounge.LoungeChat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoungeChatResponse {

  private PersonaSimpleResponse persona;

  private String content;

  private LocalDateTime createdAt;

  public LoungeChatResponse(LoungeChat loungeChat){
    this.persona = new PersonaSimpleResponse(loungeChat.getPersona());
    this.content = loungeChat.getContent();
    this.createdAt = loungeChat.getCreatedAt();
  }

}
