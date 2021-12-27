package com.eureka.ationserver.domain.insight;

import com.eureka.ationserver.domain.persona.PersonaCharm;
import com.eureka.ationserver.domain.persona.PersonaInterest;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InsightMainCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insightmaincategory_id",nullable = true)
    private InsightMainCategory insightMainCategory;

    @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL)
    private List<InsightCategory> insightSubCategoryList;


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
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
