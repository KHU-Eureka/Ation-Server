package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.Lounge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeRepository extends JpaRepository<Lounge, Long> {

}
