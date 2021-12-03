package com.eureka.ationserver.domain.persona;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaInterest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id")
    private Persona persona;

    @ManyToOne(targetEntity = Interest.class, fetch = FetchType.LAZY)
    @JoinColumn(name="interest_id")
    private Interest interest;
}



