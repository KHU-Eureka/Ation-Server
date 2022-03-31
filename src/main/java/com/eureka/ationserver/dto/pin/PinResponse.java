package com.eureka.ationserver.dto.pin;


import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.dto.pinBoard.PinBoardResponse;
import com.eureka.ationserver.model.insight.Pin;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class PinResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "핀 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "핀보드", position = 1)
    private PinBoardResponse.SimpleOut pinBoard;

    @ApiModelProperty(value = "인사이트", position = 2)
    private InsightResponse.SimpleOut insight;

    @ApiModelProperty(value = "핀태그", position = 3)
    private List<String> tagList;

    @ApiModelProperty(value = "핀 이미지", position = 4)
    private String pinImgPath;
  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class IdOut {

    @ApiModelProperty(value = "핀 id", position = 0)
    private Long id;
  }


  public static PinResponse.Out toOut(Pin pin) {
    if (pin == null) {
      return null;
    }
    Out out = new Out();
    out.id = pin.getId();
    out.pinBoard = PinBoardResponse.toSimpleOut(pin.getPinBoard());
    out.insight = InsightResponse.toSimpleOut(pin.getInsight());
    List<String> tagList = new ArrayList<>();
    pin.getPinTagList().stream().forEach(x -> tagList.add(x.getName()));
    out.tagList = tagList;
    out.pinImgPath = pin.getPinImgPath();
    return out;
  }

  public static PinResponse.IdOut toIdOut(Long id) {
    if (id == null) {
      return null;
    }
    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;
  }
}
