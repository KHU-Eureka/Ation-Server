package com.eureka.ationserver.model.insight;

import com.eureka.ationserver.model.common.AuditingEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pin extends AuditingEntity {

    public static final String PIN_PREFIX= "pin";

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

    @Column
    private String pinImgPath;

    public void setPinBoard(PinBoard pinBoard) {
        this.pinBoard = pinBoard;
    }
}
