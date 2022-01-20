package com.eureka.ationserver.dto.pinBoard;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PinBoardUpdateRequest {
    @NotEmpty
    private String name;
}
