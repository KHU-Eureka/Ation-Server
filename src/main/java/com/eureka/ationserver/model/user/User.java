package com.eureka.ationserver.model.user;

import com.eureka.ationserver.model.persona.Persona;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    public static final String USER_PREFIX= "user";

    public static final String MYPAGE_PREFIX= "my-page";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id")
    private Persona persona;

    @Column(nullable=false)
    @Setter
    private String mypageImgPath;

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
