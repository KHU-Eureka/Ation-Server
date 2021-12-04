package com.eureka.ationserver.domain.insight;

import com.eureka.ationserver.domain.persona.Persona;
import com.eureka.ationserver.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PinBoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id")
    private Persona persona;

    @Column
    private String name;

    @Column
    private String imgPath;

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public void update(Persona persona, String name) {
        this.persona = persona;
        this.name = name;
    }
}
