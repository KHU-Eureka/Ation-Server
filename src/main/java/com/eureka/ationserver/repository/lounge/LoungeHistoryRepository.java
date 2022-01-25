package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeHistoryRepository extends JpaRepository<LoungeHistory, Long> {

}
