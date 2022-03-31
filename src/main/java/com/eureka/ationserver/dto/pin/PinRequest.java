package com.eureka.ationserver.dto.pin;


import com.eureka.ationserver.model.insight.Insight;
import com.eureka.ationserver.model.insight.Pin;
import com.eureka.ationserver.model.insight.PinBoard;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PinRequest {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class FromInsightIn {

        @ApiModelProperty(value = "핀보드 id", required = true, position = 0)
        private Long pinBoardId;

        @ApiModelProperty(value = "인사이트 id", required = true, position = 1)
        private Long insightId;

        public Pin toPin(PinBoard pinBoard, Insight insight) {
            return Pin.builder()
                .pinBoard(pinBoard)
                .insight(insight)
                .pinImgPath(insight.getImgPath())
                .build();

        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateInsightIn {

        @ApiModelProperty(value = "핀 url", required = true, position = 0)
        private String url;

        @ApiModelProperty(value = "핀보드 id", required = true, position = 1)
        private Long pinBoardId;

        @ApiModelProperty(value = "핀 태그", position = 2)
        private List<String> tagList;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateIn {

        @ApiModelProperty(value = "핀보드 id", required = true, position = 0)
        private Long pinBoardId;

        @ApiModelProperty(value = "핀 태그", position = 1)
        private List<String> tagList;
    }


}
