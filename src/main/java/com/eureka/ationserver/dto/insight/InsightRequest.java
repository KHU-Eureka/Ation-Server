package com.eureka.ationserver.dto.insight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightRequest {
    private String url;
    private Long insightMainCategoryId;
    private Long insightSubCategoryId;
}
