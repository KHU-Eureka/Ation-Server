package com.eureka.ationserver.domain.insight;

import com.eureka.ationserver.domain.persona.PersonaCharm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Insight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InsightMainCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insightmaincategory_id")
    private InsightMainCategory insightMainCategory;

    @ManyToOne(targetEntity = InsightSubCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insightsubcategory_id")
    private InsightSubCategory insightSubCategory;


    @OneToMany(mappedBy = "insight", cascade = CascadeType.ALL)
    private List<InsightTag> insightTagList;

    @Column
    private String url;

    @Column
    private String title;

    @Column
    private String imgPath;

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


}
