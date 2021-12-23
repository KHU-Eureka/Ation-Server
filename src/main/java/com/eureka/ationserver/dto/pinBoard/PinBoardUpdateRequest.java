package com.eureka.ationserver.dto.pinBoard;

import com.eureka.ationserver.domain.insight.PinBoard;
import com.eureka.ationserver.domain.persona.Persona;
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
