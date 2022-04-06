package com.eureka.ationserver.dto.ideation;

import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.persona.Persona;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class IdeationRequest {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class In{

    @ApiModelProperty(value = "페르소나 id", required = true, position = 0)
    private Long personaId;

    @ApiModelProperty(value = "아이데이션 제목", position = 1)
    private String title;

    @ApiModelProperty(value = "화이트보드", position = 2)
    private String whiteboard;

    public Ideation toIdeation(Persona persona, String imgPath){
      return Ideation.builder()
          .persona(persona)
          .imgPath(imgPath)
          .title(this.title)
          .whiteboard(this.whiteboard)
          .build();
    }

  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class TitleIn{

    @ApiModelProperty(value = "아이데이션 제목", position = 0)
    private String title;

  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class WhiteboardIn{

    @ApiModelProperty(value = "아이데이션 화이트보드", position = 0)
    private String whiteboard;

  }







}
