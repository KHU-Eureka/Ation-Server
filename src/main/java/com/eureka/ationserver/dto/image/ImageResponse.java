package com.eureka.ationserver.dto.image;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ImageResponse {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "이미지")
    private String image;
  }

}
