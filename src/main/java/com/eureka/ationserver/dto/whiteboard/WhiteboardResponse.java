package com.eureka.ationserver.dto.whiteboard;

import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.lounge.Lounge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WhiteboardResponse {

  private String whiteboard;

  public WhiteboardResponse(Ideation ideation){
    this.whiteboard = ideation.getWhiteboard();
  }

  public WhiteboardResponse(Lounge lounge){
    this.whiteboard = lounge.getWhiteboard();
  }

}
