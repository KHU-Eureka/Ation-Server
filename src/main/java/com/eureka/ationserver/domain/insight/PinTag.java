package com.eureka.ationserver.domain.insight;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PinTag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = InsightPin.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insightpin_id")
    private InsightPin insightPin;

    @Column
    private String name;
}
