package com.eureka.ationserver.dto.pin;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.dto.insight.InsightMainCategoryResponse;
import com.eureka.ationserver.dto.insight.InsightSubCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightPinResponse {
    private Long id;

    private String url;

    private String title;

    private String imgPath;

    private String description;

    private String siteName;

    private String icon;

    private LocalDateTime createdAt;

    public InsightPinResponse(Insight insight){
        this.id = insight.getId();
        this.url = insight.getUrl();
        this.title = insight.getTitle();
        this.imgPath = insight.getImgPath();
        this.description = insight.getDescription();
        this.siteName = insight.getSiteName();
        this.createdAt = insight.getCreatedAt();
        this.icon = insight.getIcon();
    }

}
