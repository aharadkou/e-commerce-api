package com.epam.training.data.dto;

import io.github.benas.randombeans.annotation.Exclude;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@Data
public class ProductDto {

    @Exclude
    private Long id;

    @NotBlank
    private String name;

    private String description;

    @Valid
    private PriceDto price;

    @Exclude
    private Long categoryId;

}
