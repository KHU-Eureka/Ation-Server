package com.eureka.ationserver.dto.chat;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.lounge.LoungeChat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketChatResponse {

  private PersonaResponse.SimpleOut persona;

  private String content;

  private LocalDateTime createdAt;

  public SocketChatResponse(LoungeChat loungeChat){
    this.persona = PersonaResponse.toSimpleOut(loungeChat.getPersona());
    this.content = loungeChat.getContent();
    this.createdAt = loungeChat.getCreatedAt();
  }
}
