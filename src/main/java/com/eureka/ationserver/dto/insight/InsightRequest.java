package com.eureka.ationserver.dto.insight;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.InsightMainCategory;
import com.eureka.ationserver.domain.insight.InsightSubCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightRequest {

    @NotEmpty
    private String url;

    private Long insightMainCategoryId;

    private List<Long> insightSubCategoryIdList;

    private List<String> tagList;

}
