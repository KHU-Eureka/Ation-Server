package com.eureka.ationserver.model.ideation;

import com.eureka.ationserver.dto.ideation.IdeationRequest;
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
public class Ideation {

  public static final String IDEATION_PREFIX= "ideation";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String title;

  @Column
  private String imgPath;

  @Column
  private String whiteboard;

  @Column
  private LocalDateTime createdAt;

  @PrePersist
  public void createdAt(){
    this.createdAt = LocalDateTime.now();
  }

  @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
  @JoinColumn(name="persona_id")
  private Persona persona;

  public Ideation update(IdeationRequest ideationRequest) {
    this.title = ideationRequest.getTitle();
    this.whiteboard = ideationRequest.getWhiteboard();
    return this;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }


}
