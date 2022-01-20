package com.eureka.ationserver.model.insight;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
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

    @Column
    private LocalDateTime createdAt;

    @Column
    private String pinImgPath;


    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }

    public void setPinBoard(PinBoard pinBoard) {
        this.pinBoard = pinBoard;
    }
}
