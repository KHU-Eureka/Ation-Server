package com.eureka.ationserver.model.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String task;

    @ManyToOne(targetEntity = MainCategory.class, fetch = FetchType.LAZY)
    @JoinColumn(name="main_category_id")
    private MainCategory mainCategory;

}
