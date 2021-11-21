package com.eureka.sensationserver.domain.persona;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PersonaCharm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id")
    private Persona persona;

    @Column
    private String name;
}

