package com.eureka.ationserver.dto.insight;

import java.util.List;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightRequest {

    @NotEmpty
    private String url;

    private Long mainCategoryId;

    private List<Long> subCategoryIdList;

    private List<String> tagList;

}
