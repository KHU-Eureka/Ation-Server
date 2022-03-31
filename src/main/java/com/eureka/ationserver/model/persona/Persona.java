package com.eureka.ationserver.model.persona;

import com.eureka.ationserver.dto.persona.PersonaRequest;
import com.eureka.ationserver.model.ideation.Ideation;
import com.eureka.ationserver.model.insight.PinBoard;
import com.eureka.ationserver.model.user.User;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

  @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
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

  public Persona update(PersonaRequest.In in) {
    this.nickname = in.getNickname();
    this.age = in.getAge();
    this.gender = in.getGender();
    this.mbti = in.getMbti();
    this.job = in.getJob();
    this.introduction = in.getIntroduction();
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
