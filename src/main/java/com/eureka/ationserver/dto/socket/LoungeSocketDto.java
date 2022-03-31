package com.eureka.ationserver.dto.socket;

import com.eureka.ationserver.dto.lounge.status.LoungeMemberStatus;
import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.lounge.LoungeChat;
import com.eureka.ationserver.model.lounge.status.LoungeStatus;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class LoungeSocketDto {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ChatIn {

    private Long personaId;

    private String content;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class ChatOut {

    private PersonaResponse.SimpleOut persona;

    private String content;

    private LocalDateTime createdAt;

  }


  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Status {

    private Long loungeId;

    private LoungeStatus status;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Member {

    private PersonaResponse.SimpleOut persona;

    private LoungeMemberStatus status;
  }


  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Notice {

    private String notice;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Whiteboard {

    private String whiteboard;

  }

  public static ChatOut toChatOut(LoungeChat loungeChat) {
    if (loungeChat == null) {
      return null;
    }

    ChatOut chatOut = new ChatOut();
    chatOut.persona = PersonaResponse.toSimpleOut(loungeChat.getPersona());
    chatOut.content = loungeChat.getContent();
    chatOut.createdAt = loungeChat.getCreatedAt();
    return chatOut;
  }
}
