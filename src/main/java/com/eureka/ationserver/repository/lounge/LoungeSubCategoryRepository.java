package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import com.eureka.ationserver.model.persona.PersonaSense;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeSubCategoryRepository extends JpaRepository<LoungeSubCategory, Long> {
  void deleteByLounge_Id(@Param(value="loungeId")Long loungeId);

}
