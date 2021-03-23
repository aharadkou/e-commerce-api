package com.epam.training.data;

import io.github.benas.randombeans.annotation.Exclude;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Category {

    @Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Exclude
    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @Exclude
    @ManyToOne
    private Category superCategory;

    @Exclude
    @ManyToOne
    private Category subCategory;


}
