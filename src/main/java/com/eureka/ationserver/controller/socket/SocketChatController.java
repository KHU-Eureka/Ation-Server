package com.eureka.ationserver.controller.socket;

import com.eureka.ationserver.dto.chat.SocketChatRequest;
import com.eureka.ationserver.dto.chat.SocketChatResponse;
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
  public SocketChatResponse chatHandler(@DestinationVariable Long loungeId, SocketChatRequest socketChatRequest){
    return chatService.sendChat(loungeId, socketChatRequest);
  }
}
