package com.eureka.ationserver.dto.persona.category;

import com.eureka.ationserver.model.persona.Interest;
import com.eureka.ationserver.model.persona.PersonaInterest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class InterestResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    private Long interestId;
    private String name;
  }

  public static InterestResponse.Out toOut(PersonaInterest personaInterest) {
    if (personaInterest == null) {
      return null;
    }
    Out out = new Out();
    out.interestId = personaInterest.getInterest().getId();
    out.name = personaInterest.getInterest().getName();

    return out;
  }

  public static InterestResponse.Out toOut(Interest interest) {

    if (interest == null) {
      return null;
    }
    Out out = new Out();
    out.interestId = interest.getId();
    out.name = interest.getName();

    return out;

  }
}
