package com.eureka.ationserver.domain.insight;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InsightSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToOne(targetEntity = InsightMainCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insightmaincategory_id")
    private InsightMainCategory insightMainCategory;

}
