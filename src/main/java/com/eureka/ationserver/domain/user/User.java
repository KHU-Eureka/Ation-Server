package com.eureka.ationserver.domain.user;

import com.eureka.ationserver.domain.persona.Persona;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
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

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
