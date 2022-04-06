package com.eureka.ationserver.model.user;

import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.security.OAuthProvider;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
public class User {

  public static final String USER_PREFIX = "user";

  public static final String MYPAGE_PREFIX = "my-page";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  /**
   * social login unique Id with provider name
   */
  @Column(nullable = false, unique = true)
  private String identifyId;

  @Column
  private String name;

  @Column
  private String email;

  @Column
  private String profileImg;

  @Column
  @Enumerated(EnumType.STRING)
  private OAuthProvider provider;

  @Column(nullable = false)
  private String myPageImgPath;

  @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "persona_id")
  private Persona persona;

  public User update(String name, String email, String profileImg) {
    this.name = name;
    this.email = email;
    this.profileImg = profileImg;
    return this;
  }
}
