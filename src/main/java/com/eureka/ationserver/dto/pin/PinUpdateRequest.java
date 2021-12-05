package com.eureka.ationserver.dto.pin;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinUpdateRequest {
    private Long pinBoardId;
    private List<String> tagList;

}
