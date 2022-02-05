package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeTagRepository extends JpaRepository<LoungeTag, Long> {
  void deleteByLounge_Id(@Param(value="loungeId")Long loungeId);

}
