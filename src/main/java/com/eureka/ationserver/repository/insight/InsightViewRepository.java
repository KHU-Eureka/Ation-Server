package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.Insight;
import com.eureka.ationserver.model.insight.InsightView;
import com.eureka.ationserver.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface InsightViewRepository extends JpaRepository<InsightView, Long> {
    Optional<InsightView> findByUserAndInsight(User user, Insight insight);

}
