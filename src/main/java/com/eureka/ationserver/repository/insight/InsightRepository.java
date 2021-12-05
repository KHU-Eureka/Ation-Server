package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InsightRepository extends JpaRepository<Insight, Long> {
    List<Insight> findByOpen(Boolean open);
    List<Insight> findByOpenAndTitleContaining(boolean open, String keyword);
}
