package com.eureka.ationserver.dto.error;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class ErrorResponse {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "메시지")
    private String message;

  }

  public static ErrorResponse.Out toOut(String message) {
    Out out = new Out();
    out.message = message;
    return out;
  }

}
