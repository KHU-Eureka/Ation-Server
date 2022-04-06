package com.eureka.ationserver.socket;

import com.eureka.ationserver.dto.socket.LoungeSocketDto;
import com.eureka.ationserver.dto.socket.LoungeSocketDto.ChatOut;
import com.eureka.ationserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketChatController {

  private final ChatService chatService;

  @MessageMapping("/lounge/{loungeId}/chat/receive")
  @SendTo("/lounge/{loungeId}/chat/send")
  public ChatOut chatHandler(@DestinationVariable Long loungeId, LoungeSocketDto.ChatIn in) {
    return chatService.sendChat(loungeId, in);
  }
}
