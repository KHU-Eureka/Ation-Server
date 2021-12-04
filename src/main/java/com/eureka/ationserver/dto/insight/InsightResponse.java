package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.Insight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    private LocalDateTime createdAt;

    private InsightMainCategoryResponse insightMainCategoryResponse;

    private InsightSubCategoryResponse insightSubCategoryResponse;

    private List<String> tagList;


    public InsightResponse(Insight insight, InsightMainCategoryResponse insightMainCategoryResponse, InsightSubCategoryResponse insightSubCategoryResponse, List<String> tagList){
        this.id = insight.getId();
        this.url = insight.getUrl();
        this.title = insight.getTitle();
        this.imgPath = insight.getImgPath();
        this.description = insight.getDescription();
        this.siteName = insight.getSiteName();
        this.createdAt = insight.getCreatedAt();
        this.insightMainCategoryResponse = insightMainCategoryResponse;
        this.insightSubCategoryResponse = insightSubCategoryResponse;
        this.tagList = tagList;
    }
}
