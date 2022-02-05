package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeChat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LoungeChatRepository extends JpaRepository<LoungeChat, Long> {
  List<LoungeChat> findByLounge_Id(@Param(value="loungeId")Long loungeId);

}
