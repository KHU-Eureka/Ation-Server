package com.eureka.ationserver.model.lounge;

import com.eureka.ationserver.model.persona.Persona;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungeMember {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = Lounge.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_id")
  private Lounge lounge;

  @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "persona_id")
  private Persona persona;

  private Boolean ready;

}