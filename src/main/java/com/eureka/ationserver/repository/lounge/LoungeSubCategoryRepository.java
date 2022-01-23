package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeSubCategoryRepository extends JpaRepository<LoungeSubCategory, Long> {

}
