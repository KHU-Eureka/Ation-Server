package com.eureka.ationserver.domain.persona;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
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
