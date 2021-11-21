package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
}
