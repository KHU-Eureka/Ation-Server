package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.ELonugeStatus;
import com.eureka.ationserver.model.lounge.LoungeMember;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeMemberRepository extends JpaRepository<LoungeMember, Long> {

  Optional<LoungeMember> findByLounge_IdAndUserId(Long loungeId, Long userId);
  List<LoungeMember> findByUserIdAndLounge_Status(Long userId, ELonugeStatus status);
  List<LoungeMember> findByUserIdAndLounge_StatusAndReady(Long userId, ELonugeStatus status, Boolean ready);
  void deleteByLounge_IdAndPersona_Id(Long loungeId, Long personaId);
  void deleteByLounge_IdAndReady(Long loungeId, Boolean ready);

}
