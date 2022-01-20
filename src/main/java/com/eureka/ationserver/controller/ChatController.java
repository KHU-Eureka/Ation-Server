package com.eureka.ationserver.controller;

import com.eureka.ationserver.dto.chat.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
  @MessageMapping("/receive")
  @SendTo("/send")
  public Message ChatController(Message message){
    Message res =  Message.builder()
        .userName(message.getUserName())
        .content(message.getContent())
        .build();
    return res;
  }
}
