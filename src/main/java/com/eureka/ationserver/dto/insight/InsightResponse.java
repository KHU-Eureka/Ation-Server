package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.dto.category.CategoryResponse;
import com.eureka.ationserver.model.insight.Insight;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class InsightResponse {

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Out {

    @ApiModelProperty(value = "인사이트 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "인사이트 url", position = 1)
    private String url;

    @ApiModelProperty(value = "인사이트 제목", position = 2)
    private String title;

    @ApiModelProperty(value = "인사이트 이미지", position = 3)
    private String imgPath;

    @ApiModelProperty(value = "인사이트 설명", position = 4)
    private String description;

    @ApiModelProperty(value = "인사이트 사이트명", position = 5)
    private String siteName;

    @ApiModelProperty(value = "인사이트 아이콘", position = 6)
    private String icon;

    @ApiModelProperty(value = "인사이트 생성일시", position = 7)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "인사이트 메인 카테고리", position = 8)
    private CategoryResponse.Main mainCategory;

    @ApiModelProperty(value = "인사이트 서브 카테고리", position = 9)
    private List<CategoryResponse.Sub> subCategoryList;

    @ApiModelProperty(value = "인사이트 태그", position = 10)
    private List<String> tagList;

  }

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  public static class SimpleOut {

    @ApiModelProperty(value = "인사이트 id", position = 0)
    private Long id;

    @ApiModelProperty(value = "인사이트 url", position = 1)
    private String url;

    @ApiModelProperty(value = "인사이트 제목", position = 2)
    private String title;

    @ApiModelProperty(value = "인사이트 이미지", position = 3)
    private String imgPath;

    @ApiModelProperty(value = "인사이트 설명", position = 4)
    private String description;

    @ApiModelProperty(value = "인사이트 사이트명", position = 5)
    private String siteName;

    @ApiModelProperty(value = "인사이트 아이콘", position = 6)
    private String icon;

    @ApiModelProperty(value = "인사이트 생성일시", position = 7)
    private LocalDateTime createdAt;

  }

  public static class IdOut {

    @ApiModelProperty(value = "인사이트 id", position = 0)
    private Long id;

  }

  public static InsightResponse.Out toOut(Insight insight) {

    if (insight == null) {
      return null;
    }
    Out out = new Out();
    CategoryResponse.Main mainCategoryResponse = CategoryResponse.toMain(
        insight.getInsightMainCategory());
    List<CategoryResponse.Sub> subCategoryResponseList = insight.getInsightSubCategoryList()
        .stream().map(CategoryResponse::toSub).collect(Collectors.toList());
    List<String> tagList = new ArrayList<>();
    insight.getInsightTagList().forEach(x -> tagList.add(x.getName()));

    out.id = insight.getId();
    out.url = insight.getUrl();
    out.title = insight.getTitle();
    out.imgPath = insight.getImgPath();
    out.description = insight.getDescription();
    out.siteName = insight.getSiteName();
    out.createdAt = insight.getCreatedAt();
    out.mainCategory = mainCategoryResponse;
    out.subCategoryList = subCategoryResponseList;
    out.tagList = tagList;
    out.icon = insight.getIcon();

    return out;
  }

  public static InsightResponse.SimpleOut toSimpleOut(Insight insight) {

    if (insight == null) {
      return null;
    }
    SimpleOut out = new SimpleOut();
    List<String> tagList = new ArrayList<>();
    insight.getInsightTagList().forEach(x -> tagList.add(x.getName()));

    out.id = insight.getId();
    out.url = insight.getUrl();
    out.title = insight.getTitle();
    out.imgPath = insight.getImgPath();
    out.description = insight.getDescription();
    out.siteName = insight.getSiteName();
    out.createdAt = insight.getCreatedAt();
    out.icon = insight.getIcon();

    return out;
  }

  public static InsightResponse.IdOut toIdOut(Long id) {
    if (id == null) {
      return null;
    }
    IdOut idOut = new IdOut();
    idOut.id = id;
    return idOut;
  }

}
