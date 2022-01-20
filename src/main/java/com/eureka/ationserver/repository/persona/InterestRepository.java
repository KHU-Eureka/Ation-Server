package com.eureka.ationserver.repository.persona;

import com.eureka.ationserver.model.persona.Interest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface InterestRepository extends JpaRepository<Interest,Long> {
    List<Interest> findAllByIdIn(List<Long> id);

}
