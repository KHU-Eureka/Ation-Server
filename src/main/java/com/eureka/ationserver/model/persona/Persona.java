package com.eureka.ationserver.model.persona;

import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.insight.PinBoard;
import com.eureka.ationserver.model.user.User;
import com.eureka.ationserver.dto.persona.PersonaRequest;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

  public static final String PERSONA_PREFIX = "persona";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(unique = true)
  private String nickname;

  @Column
  private Integer age;

  @Column
  private Integer gender;

  @Column
  private String mbti;

  @Column
  private String job;

  @Column
  private String profileImgPath;

  @Column
  private String introduction;

  @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
  private List<PersonaSense> personaSenseList;

  @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
  private List<PersonaCharm> personaCharmList;

  @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
  private List<PersonaInterest> personaInterestList;

  public Persona update(PersonaRequest personaRequest) {
    this.nickname = personaRequest.getNickname();
    this.age = personaRequest.getAge();
    this.gender = personaRequest.getGender();
    this.mbti = personaRequest.getMbti();
    this.job = personaRequest.getJob();
    this.introduction = personaRequest.getIntroduction();
    return this;
  }

  @OneToMany(mappedBy = "persona", cascade = CascadeType.REMOVE)
  private List<PinBoard> pinBoardList;

  @OneToMany(mappedBy = "persona", cascade = CascadeType.REMOVE)
  private List<Ideation> ideationList;

  public void setProfileImgPath(String personaImgPath) {
    this.profileImgPath = personaImgPath;
  }
}
