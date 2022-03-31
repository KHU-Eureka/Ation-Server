package com.eureka.ationserver.dto.pinBoard;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.model.insight.PinBoard;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PinBoardResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "핀보드 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "페르소나", position = 1)
    private PersonaResponse.SimpleOut persona;

    @ApiModelProperty(value = "핀보드 이름", position = 2)
    private String name;

    @ApiModelProperty(value = "핀보드 이미지", position = 3)
    private String imgPath;

    @ApiModelProperty(value = "핀보드 핀 개수", position = 4)
    private Long totalPinNumber;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class SimpleOut {

    @ApiModelProperty(value = "핀보드 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "페르소나", position = 1)
    private PersonaResponse.SimpleOut persona;

    @ApiModelProperty(value = "핀보드 이름", position = 2)
    private String name;

    @ApiModelProperty(value = "핀보드 이미지", position = 3)
    private String imgPath;

  }

  public static class IdOut {

    @ApiModelProperty(value = "핀보드 id", position = 0)
    private Long id;
  }

  public static PinBoardResponse.Out toOut(PinBoard pinBoard, Long totalPinNumber) {

    if (pinBoard == null) {
      return null;
    }

    Out out = new Out();

    out.id = pinBoard.getId();
    out.persona = PersonaResponse.toSimpleOut(pinBoard.getPersona());
    out.name = pinBoard.getName();
    out.imgPath = pinBoard.getImgPath();
    out.totalPinNumber = totalPinNumber;

    return out;
  }

  public static PinBoardResponse.SimpleOut toSimpleOut(PinBoard pinBoard) {

    if (pinBoard == null) {
      return null;
    }

    SimpleOut simpleOut = new SimpleOut();

    simpleOut.id = pinBoard.getId();
    simpleOut.persona = PersonaResponse.toSimpleOut(pinBoard.getPersona());
    simpleOut.name = pinBoard.getName();
    simpleOut.imgPath = pinBoard.getImgPath();

    return simpleOut;
  }

  public static PinBoardResponse.IdOut toIdOut(Long id) {
    if (id == null) {
      return null;
    }

    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;
  }
}
