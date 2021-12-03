package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsightRepository extends JpaRepository<Insight, Long> {
}
