package com.eureka.sensationserver.domain.persona;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonaJob {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Persona.class, fetch = FetchType.LAZY)
    @JoinColumn(name="persona_id")
    private Persona persona;

    @ManyToOne(targetEntity = Sense.class, fetch = FetchType.LAZY)
    @JoinColumn(name="job_id")
    private Job job;
}


