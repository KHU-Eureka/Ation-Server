package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.model.lounge.ELonugeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocketLoungeStatusResponse {
  private ELonugeStatus status;
}
