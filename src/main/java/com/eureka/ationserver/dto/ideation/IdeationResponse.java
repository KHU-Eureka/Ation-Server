package com.eureka.ationserver.dto.ideation;

import com.eureka.ationserver.dto.persona.PersonaResponse;
import com.eureka.ationserver.dto.persona.PersonaResponse.IdOut;
import com.eureka.ationserver.model.ideation.Ideation;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class IdeationResponse {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "아이데이션 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "페르소나", position = 1)
    private PersonaResponse.Out persona;

    @ApiModelProperty(value = "아이데이션 제목", position = 2)
    private String title;

    @ApiModelProperty(value = "아이데이션 이미지", position = 3)
    private String imgPath;

    @ApiModelProperty(value = "아이데이션 화이트보드", position = 4)
    private String whiteboard;

    @ApiModelProperty(value = "아이데이션 생성일자", position = 5)
    private LocalDateTime createdAt;

  }

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class IdOut {

    @ApiModelProperty(value = "아이데이션 id", position = 0)
    private Long id;

  }

  public static IdeationResponse.Out toOut(Ideation ideation){

    if(ideation == null){
      return null;
    }

    Out out = new Out();
    out.id = ideation.getId();
    out.persona = PersonaResponse.toOut(ideation.getPersona());
    out.title = ideation.getTitle();
    out.whiteboard = ideation.getWhiteboard();
    out.imgPath = ideation.getImgPath();
    out.createdAt = ideation.getCreatedAt();

    return out;

  }

  public static IdeationResponse.IdOut toIdOut(Long id) {

    if (id == null) {
      return null;
    }

    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;

  }

}
