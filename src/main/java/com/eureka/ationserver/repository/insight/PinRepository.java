package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinRepository extends JpaRepository<Pin, Long> {
}
