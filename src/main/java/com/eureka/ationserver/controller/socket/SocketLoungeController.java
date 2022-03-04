package com.eureka.ationserver.controller.socket;

import com.eureka.ationserver.dto.common.SimpleValueResponse;
import com.eureka.ationserver.dto.lounge.SocketLoungeStatusResponse;
import com.eureka.ationserver.dto.lounge.SocketMemberResponse;
import com.eureka.ationserver.dto.whiteboard.WhiteboardRequest;
import com.eureka.ationserver.dto.whiteboard.WhiteboardResponse;
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
  public SocketMemberResponse memberHandler(@DestinationVariable Long loungeId,
      SocketMemberResponse socketMemberResponse) {
    return socketMemberResponse;
  }

  @MessageMapping("/lounge/{loungeId}/status/receive")
  @SendTo("/lounge/{loungeId}/status/send")
  public SocketLoungeStatusResponse statusHandler(@DestinationVariable Long loungeId,
      SocketLoungeStatusResponse socketLoungeStatusResponse) {
    return socketLoungeStatusResponse;
  }

  @MessageMapping("/lounge/{loungeId}/notice/receive")
  @SendTo("/lounge/{loungeId}/notice/send")
  public SimpleValueResponse<String> statusHandler(@DestinationVariable Long loungeId,
      SimpleValueResponse<String> notice) {
    return notice;
  }

  @MessageMapping("/lounge/{loungeId}/whiteboard/receive")
  @SendTo("/lounge/{loungeId}/whiteboard/send")
  public WhiteboardResponse whiteboardHandler(@DestinationVariable Long loungeId,
      WhiteboardRequest whiteboardRequest) {
    loungeService.updateWhiteboard(loungeId, whiteboardRequest);
    return WhiteboardResponse.builder().whiteboard(whiteboardRequest.getWhiteboard()).build();
  }


}
