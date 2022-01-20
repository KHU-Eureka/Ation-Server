package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.model.persona.Sense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SenseRepository extends JpaRepository<Sense,Long> {
    List<Sense> findAllByIdIn(List<Long> id);

}
