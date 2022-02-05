package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaSimpleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketMemberResponse {

  private PersonaSimpleResponse persona;

  private EMemberStatus status;

}

