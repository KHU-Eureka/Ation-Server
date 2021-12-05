package com.eureka.ationserver.dto.pin;


import com.eureka.ationserver.domain.insight.Pin;
import com.eureka.ationserver.dto.insight.InsightResponse;
import com.eureka.ationserver.dto.pinBoard.PinBoardResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinResponse {
    private Long id;

    private PinBoardResponse pinBoard;

    private InsightResponse insight;

    private List<String> tagList;

    public PinResponse(Pin pin){
        System.out.println(pin.toString());
        this.pinBoard = new PinBoardResponse(pin.getPinBoard());
        this.insight = new InsightResponse(pin.getInsight());
        List<String> tagList = new ArrayList<>();
        pin.getPinTagList().stream().forEach(x-> tagList.add(x.getName()));
        System.out.println(tagList);
        this.tagList = tagList;
    }
}
