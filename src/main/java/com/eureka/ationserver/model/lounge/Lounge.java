package com.eureka.ationserver.model.lounge;

import com.eureka.ationserver.dto.lounge.LoungeRequest;
import com.eureka.ationserver.model.category.MainCategory;
import com.eureka.ationserver.model.persona.Persona;
import com.eureka.ationserver.model.persona.Sense;
import java.time.LocalDateTime;
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
public class Lounge {

  public static final String Lounge_PREFIX = "lounge";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
  @JoinColumn(name="persona_id")
  private Persona persona;

  @ManyToOne(targetEntity = Sense.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "sense_id")
  private Sense sense;

  @ManyToOne(targetEntity = MainCategory.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "main_category_id", nullable = true)
  private MainCategory loungeMainCategory;

  @OneToMany(mappedBy = "lounge", cascade = CascadeType.ALL)
  private List<LoungeSubCategory> loungeSubCategoryList;


  @OneToMany(mappedBy = "lounge", cascade = CascadeType.ALL)
  private List<LoungeTag> loungeTagList;

  @Column
  private String title;

  @Column
  private Integer limit;

  @Column
  private Integer status; // 0 : 모집 중, 1 : 종료, 2: 진행 중

  @Column
  private String introduction;

  @Column
  private String imgPath;

  @Column
  private LocalDateTime createdAt;


  @PrePersist
  public void createdAt() {
    this.createdAt = LocalDateTime.now();
  }


  public Lounge update(LoungeRequest loungeRequest, MainCategory mainCategory, Sense sense) {
    this.title = loungeRequest.getTitle();
    this.limit = loungeRequest.getLimit();
    this.status = loungeRequest.getStatus();
    this.introduction = loungeRequest.getIntroduction();
    this.sense = sense;
    this.loungeMainCategory = mainCategory;
    return this;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

}
