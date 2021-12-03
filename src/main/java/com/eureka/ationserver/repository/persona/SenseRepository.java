package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.domain.persona.Sense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SenseRepository extends JpaRepository<Sense,Long> {
    List<Sense> findAllByIdIn(List<Long> id);

}
