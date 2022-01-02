package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Insight;
import com.eureka.ationserver.domain.insight.InsightView;
import com.eureka.ationserver.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface InsightViewRepository extends JpaRepository<InsightView, Long> {
    Optional<InsightView> findByUserAndInsight(User user, Insight insight);

}
