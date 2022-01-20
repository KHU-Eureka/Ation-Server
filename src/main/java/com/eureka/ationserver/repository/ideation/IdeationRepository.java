package com.eureka.ationserver.repository.ideation;

import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.insight.PinBoard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IdeationRepository extends JpaRepository<Ideation, Long> {
  List<Ideation> findByPersona_Id(@Param(value="personaId")Long personaId);
}
