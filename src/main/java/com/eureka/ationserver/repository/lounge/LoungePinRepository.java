package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungePin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungePinRepository extends JpaRepository<LoungePin, Long> {

}
