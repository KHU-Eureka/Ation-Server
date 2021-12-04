package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.Insight;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    public InsightResponse(Insight insight){
        id = insight.getId();
        url = insight.getUrl();
        title = insight.getTitle();
        imgPath = insight.getImgPath();
        description = insight.getDescription();
        siteName = insight.getSiteName();
        createdAt = insight.getCreatedAt();
    }
}
