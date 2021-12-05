package com.eureka.ationserver.domain.insight;

import com.eureka.ationserver.domain.persona.PersonaCharm;
import com.eureka.ationserver.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;


@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsightPin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PinBoard.class, fetch = FetchType.LAZY)
    @JoinColumn(name="pinboard_id")
    private PinBoard pinBoard;

    @ManyToOne(targetEntity = Insight.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insight_id")
    private Insight insight;

    @OneToMany(mappedBy = "insightPin", cascade = CascadeType.ALL)
    private List<PinTag> pinTagList;



}
