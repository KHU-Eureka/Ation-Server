package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.domain.insight.PinBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PinBoardRepository extends JpaRepository<PinBoard, Long> {
    List<PinBoard> findByPersona_Id(@Param(value="personaId")Long personaId);
}
