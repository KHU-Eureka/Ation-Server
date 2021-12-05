package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface InsightRepository extends JpaRepository<Insight, Long> {
    List<Insight> findByOpen(Boolean open);
    Set<Insight> findByOpenAndTitleContainingOrInsightTagList_NameContaining(boolean open, String keyword1, String keyword2);
}
