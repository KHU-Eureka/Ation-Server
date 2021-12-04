package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.PinBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PinBoardRepository extends JpaRepository<PinBoard, Long> {
}
