package com.eureka.ationserver.controller.socket;

import com.eureka.ationserver.dto.lounge.SocketLoungeStatusResponse;
import com.eureka.ationserver.dto.lounge.SocketMemberResponse;
import com.eureka.ationserver.service.LoungeService;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller

public class SocketLoungeController {

  @MessageMapping("/lounge/{loungeId}/member/receive")
  @SendTo("/lounge/{loungeId}/member/send")
  public SocketMemberResponse memberHandler(@DestinationVariable Long loungeId, SocketMemberResponse socketMemberResponse){
    return socketMemberResponse;
  }

  @MessageMapping("/lounge/{loungeId}/status/receive")
  @SendTo("/lounge/{loungeId}/status/send")
  public SocketLoungeStatusResponse memberHandler(@DestinationVariable Long loungeId, SocketLoungeStatusResponse socketLoungeStatusResponse){
    return socketLoungeStatusResponse;
  }



}
