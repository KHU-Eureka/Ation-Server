package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.dto.category.MainCategoryResponse;
import com.eureka.ationserver.dto.category.SubCategoryResponse;
import com.eureka.ationserver.model.insight.Insight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String icon;

    private LocalDateTime createdAt;

    private MainCategoryResponse insightMainCategory;

    private List<SubCategoryResponse> insightSubCategoryList;

    private List<String> tagList;


    public InsightResponse(Insight insight){

        MainCategoryResponse mainCategoryResponse = new MainCategoryResponse(insight.getInsightMainCategory());
        List<SubCategoryResponse> subCategoryResponseList = new ArrayList<>();
        insight.getInsightSubCategoryList().stream().forEach(x-> subCategoryResponseList.add(new SubCategoryResponse(x)));
        List<String> tagList = new ArrayList<>();
        insight.getInsightTagList().stream().forEach(x-> tagList.add(x.getName()));

        this.id = insight.getId();
        this.url = insight.getUrl();
        this.title = insight.getTitle();
        this.imgPath = insight.getImgPath();
        this.description = insight.getDescription();
        this.siteName = insight.getSiteName();
        this.createdAt = insight.getCreatedAt();
        this.insightMainCategory = mainCategoryResponse;
        this.insightSubCategoryList = subCategoryResponseList;
        this.tagList = tagList;
        this.icon = insight.getIcon();
    }

}
