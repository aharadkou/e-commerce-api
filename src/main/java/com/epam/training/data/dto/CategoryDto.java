package com.epam.training.data.dto;

import io.github.benas.randombeans.annotation.Exclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CategoryDto {

    @Exclude
    private Long id;

    @NotBlank
    private String name;

    @Exclude
    private Long superCategoryId;

    @Exclude
    private Long subCategoryId;
}
