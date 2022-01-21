package com.eureka.ationserver.repository.category;

import com.eureka.ationserver.model.category.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {
    List<SubCategory> findByMainCategory_Id(@Param(value="mainCategoryId") Long insightMainCategoryId);
    List<SubCategory> findAllByIdIn(List<Long> id);

}
