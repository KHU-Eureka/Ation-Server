package com.eureka.ationserver.repository.insight;

import com.eureka.ationserver.model.insight.PinTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface PinTagRepository extends JpaRepository<PinTag, Long> {
    List<PinTag> deleteByPin_Id(@Param(value="pinId")Long pinId);

}
