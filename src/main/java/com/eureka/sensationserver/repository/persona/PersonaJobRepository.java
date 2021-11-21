package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.PersonaCharm;
import com.eureka.sensationserver.domain.persona.PersonaJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaJobRepository extends JpaRepository<PersonaJob, Long> {
    List<PersonaJob> findByPersona_Id(@Param(value="persona_id")Long personaId);

}
