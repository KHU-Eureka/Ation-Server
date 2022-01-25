package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.chat.ChatRequest;
import com.eureka.ationserver.dto.chat.ChatResponse;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  @MessageMapping("/rounge-receive/{roungeId}")
  @SendTo("/rounge-send/{roungeId}")
  public ChatResponse roungeChat(ChatRequest chatRequest){
    ChatResponse chatResponse =  ChatResponse.builder()
        .userId(chatRequest.getUserId())
        .content(chatRequest.getContent())
        .build();
    return chatResponse;
  }
}
