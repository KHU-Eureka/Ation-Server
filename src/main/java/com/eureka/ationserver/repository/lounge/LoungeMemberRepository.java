package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.ELoungeStatus;
import com.eureka.ationserver.model.lounge.LoungeMember;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeMemberRepository extends JpaRepository<LoungeMember, Long> {

  Optional<LoungeMember> findByLounge_IdAndUserId(Long loungeId, Long userId);

  List<LoungeMember> findByUserIdAndLounge_Status(Long userId, ELoungeStatus status);

  List<LoungeMember> findByUserIdAndLounge_StatusAndReady(Long userId, ELoungeStatus status,
      Boolean ready);

  void deleteByLounge_IdAndUserId(Long loungeId, Long userId);

  void deleteByLounge_IdAndPersona_Id(Long loungeId, Long personaId);

  void deleteByLounge_IdAndReady(Long loungeId, Boolean ready);

}
