package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.domain.persona.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest,Long> {
    List<Interest> findAllByIdIn(List<Long> id);

}
