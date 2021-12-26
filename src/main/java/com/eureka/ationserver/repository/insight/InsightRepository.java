package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface InsightRepository extends JpaRepository<Insight, Long> {
    List<Insight> findByOpenOrderByCreatedAtDesc(Boolean open);
    Set<Insight> findByOpenAndTitleContainingOrInsightMainCategoryNameContainingOrInsightSubCategoryList_InsightSubCategoryNameContainingOrInsightTagList_NameContainingOrderByCreatedAtDesc(boolean open, String keyword1, String keyword2, String keyword3, String keyword4);
    List<Insight> findByInsightMainCategoryIdOrderByCreatedAtDesc(Long insightMainCategoryId);
}
