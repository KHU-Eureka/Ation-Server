package com.eureka.ationserver.model.insight;

import com.eureka.ationserver.model.category.MainCategory;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insight {

  public static final String INSIGHT_PREFIX = "insight";

  public static final String INSIGHT_ICON_PREFIX = "icon";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = MainCategory.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "main_category_id", nullable = true)
  private MainCategory insightMainCategory;

  @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL)
  private List<InsightSubCategory> insightSubCategoryList;


  @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL)
  private List<InsightTag> insightTagList;


  @Column
  private String url;

  @Column
  private String title;

  @Column
  private String imgPath;

  @Column
  private String icon;

  @Column
  private String description;

  @Column
  private String siteName;

  @Column
  private boolean open;

  @Column
  private LocalDateTime createdAt;


  @PrePersist
  public void createdAt() {
    this.createdAt = LocalDateTime.now();
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }
}
