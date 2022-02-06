package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungePin;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoungePinRepository extends JpaRepository<LoungePin, Long> {
  Optional<LoungePin> findByLounge_IdAndUserId(Long loungeId, Long userId);
  void deleteByLounge_IdAndUserId(Long loungeId, Long userId);
  List<LoungePin> findByUserId(Long userId);

}
