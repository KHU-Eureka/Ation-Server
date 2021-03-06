package com.eureka.ationserver.model.lounge;


import com.eureka.ationserver.model.category.SubCategory;
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
public class LoungeSubCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(targetEntity = Lounge.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_id")
  private Lounge lounge;

  @ManyToOne(targetEntity = SubCategory.class, fetch = FetchType.LAZY)
  @JoinColumn(name = "lounge_subcategory_id")
  private SubCategory subCategory;

}
