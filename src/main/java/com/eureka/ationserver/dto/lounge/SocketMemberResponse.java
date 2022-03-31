package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketMemberResponse {

  private PersonaResponse.SimpleOut persona;

  private EMemberStatus status;

}

