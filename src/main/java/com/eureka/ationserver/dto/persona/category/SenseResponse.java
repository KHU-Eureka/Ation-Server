package com.eureka.ationserver.dto.persona.category;

import com.eureka.ationserver.model.persona.PersonaSense;
import com.eureka.ationserver.model.persona.Sense;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class SenseResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    private Long senseId;

    private String name;

  }

  public static SenseResponse.Out toOut(PersonaSense personaSense) {
    if (personaSense == null) {
      return null;
    }

    Out out = new Out();
    out.senseId = personaSense.getSense().getId();
    out.name = personaSense.getSense().getName();

    return out;
  }

  public static SenseResponse.Out toOut(Sense sense) {
    if (sense == null) {
      return null;
    }

    Out out = new Out();
    out.senseId = sense.getId();
    out.name = sense.getName();

    return out;
  }

}
