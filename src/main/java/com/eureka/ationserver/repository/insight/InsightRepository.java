package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightRepository extends JpaRepository<Insight, Long> {
    List<Insight> findByOpenOrderByCreatedAtDesc(Boolean open);
    List<Insight> findByOpen(Boolean open);
    Set<Insight> findByOpenAndTitleContainingOrInsightMainCategoryNameContainingOrInsightSubCategoryList_SubCategoryNameContainingOrInsightTagList_NameContainingOrderByCreatedAtDesc(boolean open, String keyword1, String keyword2, String keyword3, String keyword4);
    List<Insight> findByInsightMainCategoryIdOrderByCreatedAtDesc(Long insightMainCategoryId);
}
