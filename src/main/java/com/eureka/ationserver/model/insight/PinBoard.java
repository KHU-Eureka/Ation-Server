package com.eureka.ationserver.model.insight;

import com.eureka.ationserver.model.persona.Persona;
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
@AllArgsConstructor
@NoArgsConstructor
public class PinBoard {

    public static final String PINBOARD_PREFIX= "pin-board";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id")
    private Persona persona;

    @OneToMany(mappedBy = "pinBoard", cascade = CascadeType.REMOVE)
    private List<Pin> pinList;

    @Column
    private String name;

    @Column
    private String imgPath;

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void update(String name) {
        this.name = name;
    }

    @Column
    private LocalDateTime createdAt;


    @PrePersist
    public void createdAt(){
        this.createdAt = LocalDateTime.now();
    }
}
