package com.eureka.sensationserver.repository.persona;

import com.eureka.sensationserver.domain.persona.Job;
import com.eureka.sensationserver.domain.persona.Sense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
    List<Job> findAllByIdIn(List<Long> id);

}
