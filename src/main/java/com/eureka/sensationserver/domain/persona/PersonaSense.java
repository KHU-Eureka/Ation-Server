package com.eureka.sensationserver.domain.persona;

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
public class PersonaSense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="persona_id")
    private Persona persona;

    @ManyToOne
    @JoinColumn(name="sense_id")
    private Sense sense;
}
