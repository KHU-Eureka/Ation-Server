package com.eureka.ationserver.dto.insight;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class InsightRequest {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class In {

    @ApiModelProperty(value = "인사이트 url", required = true, position = 0)
    private String url;

    @ApiModelProperty(value = "인사이트 메인 카테고리", required = true, position = 2)
    private Long mainCategoryId;


    @ApiModelProperty(value = "인사이트 서브 카테고리", required = true, position = 3)
    private List<Long> subCategoryIdList;

    @ApiModelProperty(value = "인사이트 태그", position = 4)
    private List<String> tagList;

  }


}
