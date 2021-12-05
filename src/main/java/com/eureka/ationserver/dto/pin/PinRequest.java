package com.eureka.ationserver.dto.pin;


import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.Pin;
import com.eureka.ationserver.domain.insight.PinBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinRequest {

    @NotEmpty
    private Long pinBoardId;

    @NotEmpty
    private Long insightId;

    public Pin toEnitity(PinBoard pinBoard, Insight insight){
        return Pin.builder()
                .pinBoard(pinBoard)
                .insight(insight)
                .build();

    }

}
