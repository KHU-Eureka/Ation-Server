package com.eureka.ationserver.dto.pin;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightPinRequest {

    @NotEmpty
    private String url;

    private Long pinBoardId;

    private List<String> tagList;

    @Override
    public String toString() {
        return "InsightPinRequest{" +
                "url='" + url + '\'' +
                ", pinBoardId=" + pinBoardId +
                ", tagList=" + tagList +
                '}';
    }
}

