package com.eureka.sensationserver.domain.persona;

import com.eureka.sensationserver.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Column(unique = true)
    private String name;

    @Column
    private Integer age;

    @Column
    private String gender;

    @Column
    private String mbti;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<PersonaSense> personaSenseList;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<PersonaCharm> personaCharmList;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
    private List<PersonaJob> personaJobList;


}
