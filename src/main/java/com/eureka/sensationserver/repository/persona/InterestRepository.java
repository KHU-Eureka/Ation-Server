package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.Interest;
import com.eureka.sensationserver.domain.persona.Sense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest,Long> {
    List<Interest> findAllByIdIn(List<Long> id);

}
