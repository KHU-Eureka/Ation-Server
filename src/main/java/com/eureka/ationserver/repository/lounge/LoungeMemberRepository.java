package com.eureka.ationserver.repository.lounge;

import com.eureka.ationserver.model.lounge.LoungeMember;
import java.util.List;
import java.util.Optional;
import javax.swing.text.html.Option;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoungeMemberRepository extends JpaRepository<LoungeMember, Long> {

  Optional<LoungeMember> findByLounge_IdAndPersona_Id(@Param(value="loungeId")Long loungeId, @Param(value="personaId")Long personaId);
  void deleteByLounge_IdAndPersona_Id(@Param(value="loungeId")Long loungeId, @Param(value="personaId")Long personaId);
}
