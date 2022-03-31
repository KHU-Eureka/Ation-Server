package com.eureka.ationserver.dto.pinBoard;

import com.eureka.ationserver.model.insight.PinBoard;
import com.eureka.ationserver.model.persona.Persona;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PinBoardRequest {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class In {

    @ApiModelProperty(value = "페르소나 id", required = true, position = 0)
    private Long personaId;

    @ApiModelProperty(value = "핀보드 이름", required = true, position = 1)
    private String name;

    public PinBoard toPinBoard(Persona persona, String imgPath) {
      return PinBoard.builder()
          .persona(persona)
          .imgPath(imgPath)
          .name(name)
          .build();
    }

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UpdateIn {

    @ApiModelProperty(value = "핀보드 이름", required = true, position = 0)
    private String name;
  }
}
