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

    private String imageUrl;

    private String description;

    private String sightName;

    private LocalDateTime createdAt;

    public InsightResponse(Insight insight){
        id = insight.getId();
        url = insight.getUrl();
        title = insight.getImageUrl();
        description = insight.getDescription();
        sightName = insight.getSightName();
        createdAt = insight.getCreatedAt();
    }
}
