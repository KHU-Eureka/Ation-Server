package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.Sense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface SenseRepository extends JpaRepository<Sense,Long> {
    List<Sense> findAllByIdIn(List<Long> id);
}
