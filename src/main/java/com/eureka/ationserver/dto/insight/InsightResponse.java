package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.dto.category.CategoryResponse;
import com.eureka.ationserver.model.insight.Insight;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightResponse {

  private Long id;

  private String url;

  private String title;

  private String imgPath;

  private String description;

  private String siteName;

  private String icon;

  private LocalDateTime createdAt;

  private CategoryResponse.Main mainCategory;

  private List<CategoryResponse.Sub> subCategoryList;

  private List<String> tagList;


  public InsightResponse(Insight insight) {

    CategoryResponse.Main mainCategoryResponse = CategoryResponse.toMain(
        insight.getInsightMainCategory());
    List<CategoryResponse.Sub> subCategoryResponseList = insight.getInsightSubCategoryList()
        .stream().map(CategoryResponse::toSub).collect(Collectors.toList());
    List<String> tagList = new ArrayList<>();
    insight.getInsightTagList().forEach(x -> tagList.add(x.getName()));

    this.id = insight.getId();
    this.url = insight.getUrl();
    this.title = insight.getTitle();
    this.imgPath = insight.getImgPath();
    this.description = insight.getDescription();
    this.siteName = insight.getSiteName();
    this.createdAt = insight.getCreatedAt();
    this.mainCategory = mainCategoryResponse;
    this.subCategoryList = subCategoryResponseList;
    this.tagList = tagList;
    this.icon = insight.getIcon();
  }

}
