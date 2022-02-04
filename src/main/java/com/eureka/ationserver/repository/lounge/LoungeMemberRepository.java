package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeMemberRepository extends JpaRepository<LoungeMember, Long> {

}
