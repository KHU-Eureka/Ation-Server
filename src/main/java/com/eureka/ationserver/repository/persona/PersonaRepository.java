package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.domain.persona.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    List<Persona> findByUserId(@Param(value="userId") Long userId);
    Optional<Persona> findByNickname(@Param(value="name") String name);
}
