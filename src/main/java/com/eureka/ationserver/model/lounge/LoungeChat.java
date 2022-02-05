package com.eureka.ationserver.model.lounge;

import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.persona.Persona;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoungeChat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = Lounge.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_id", nullable = false)
  private Lounge lounge;

  @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "persona_id", nullable = false)
  private Persona persona;

  private String content;

  @Column
  private LocalDateTime createdAt;


  @PrePersist
  public void createdAt() {
    this.createdAt = LocalDateTime.now();
  }


}
