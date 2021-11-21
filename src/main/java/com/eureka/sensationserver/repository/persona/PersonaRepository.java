package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByUserId(@Param(value="userId") Long userId);
}
