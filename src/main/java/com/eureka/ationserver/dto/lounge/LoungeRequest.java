package com.eureka.ationserver.dto.lounge;

import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.lounge.Lounge;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class LoungeRequest {

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class In {

    @ApiModelProperty(value = "라운지 제목", required = true, position = 0)
    private String title;

    @ApiModelProperty(value = "페르소나 id", required = true, position = 1)
    private Long personaId;

    @ApiModelProperty(value = "라운지 멤버제한", required = true, position = 2)
    private Integer limitMember;

    @ApiModelProperty(value = "라운지 소개", required = true, position = 3)
    private String introduction;

    @ApiModelProperty(value = "라운지 이미지", required = true, position = 4)
    private Long imageId;

    @ApiModelProperty(value = "라운지 발달감각", required = true, position = 5)
    private Long senseId;

    @ApiModelProperty(value = "라운지 태그", required = true, position = 6)
    private List<String> tagList;

    @ApiModelProperty(value = "라운지 메인 카테고리", required = true, position = 7)
    private Long mainCategoryId;

    @ApiModelProperty(value = "라운지 서브 카테고리", required = true, position = 8)
    private List<Long> subCategoryIdList;

    public Lounge toLounge(Persona persona, MainCategory mainCategory, Sense sense,
        String imgPath) {
      return Lounge.builder()
          .title(this.title)
          .limitMember(this.limitMember)
          .introduction(this.introduction)
          .imgPath(imgPath)
          .loungeMainCategory(mainCategory)
          .sense(sense)
          .persona(persona)
          .build();
    }
  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class WhiteboardIn {

    @ApiModelProperty(value = "라운지 화이트보드", position = 0)
    private String whiteboard;

  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class NoticeIn {

    @ApiModelProperty(value = "라운지 공지", position = 0)
    private String notice;

  }


}