package com.eureka.ationserver.domain.insight;

import com.eureka.ationserver.domain.persona.PersonaCharm;
import com.eureka.ationserver.domain.user.User;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = PinBoard.class, fetch = FetchType.LAZY)
    @JoinColumn(name="pinboard_id")
    private PinBoard pinBoard;

    @ManyToOne(targetEntity = Insight.class, fetch = FetchType.LAZY)
    @JoinColumn(name="insight_id")
    private Insight insight;

    @OneToMany(mappedBy = "pin", cascade = CascadeType.ALL)
    private List<PinTag> pinTagList;

    @Override
    public String toString() {
        return "Pin{" +
                "id=" + id +
                ", pinBoard=" + pinBoard +
                ", insight=" + insight +
                ", pinTagList=" + pinTagList +
                '}';
    }
}
