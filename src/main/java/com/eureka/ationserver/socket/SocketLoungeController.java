package com.eureka.ationserver.socket;

import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.dto.socket.LoungeSocketDto;
import com.eureka.ationserver.service.LoungeService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SocketLoungeController {

  private final LoungeService loungeService;

  @MessageMapping("/lounge/{loungeId}/member/receive")
  @SendTo("/lounge/{loungeId}/member/send")
  public LoungeSocketDto.Member memberHandler(@DestinationVariable Long loungeId,
      LoungeSocketDto.Member obj) {
    return obj;
  }

  @MessageMapping("/lounge/{loungeId}/status/receive")
  @SendTo("/lounge/{loungeId}/status/send")
  public LoungeSocketDto.Status statusHandler(@DestinationVariable Long loungeId,
      LoungeSocketDto.Status obj) {
    return obj;
  }

  @MessageMapping("/lounge/{loungeId}/notice/receive")
  @SendTo("/lounge/{loungeId}/notice/send")
  public LoungeSocketDto.Notice noticesHandler(@DestinationVariable Long loungeId,
      LoungeSocketDto.Notice obj) {
    return obj;
  }

  @MessageMapping("/lounge/{loungeId}/whiteboard/receive")
  @SendTo("/lounge/{loungeId}/whiteboard/send")
  public LoungeSocketDto.Whiteboard whiteboardHandler(@DestinationVariable Long loungeId,
      LoungeRequest.WhiteboardIn in) {
    loungeService.updateWhiteboard(loungeId, in);
    return LoungeSocketDto.Whiteboard.builder().whiteboard(in.getWhiteboard()).build();
  }


}
