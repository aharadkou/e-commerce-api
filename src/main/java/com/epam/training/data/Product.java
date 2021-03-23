package com.epam.training.data;

import io.github.benas.randombeans.annotation.Exclude;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Product {

    @Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String description;

    @Embedded
    private Price price;

    @ManyToOne
    private Category category;

}
