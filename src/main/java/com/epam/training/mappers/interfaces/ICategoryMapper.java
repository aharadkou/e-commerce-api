package com.epam.training.mappers.interfaces;

import com.epam.training.data.Category;
import com.epam.training.data.dto.CategoryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICategoryMapper extends IEntityMapper<Category, CategoryDto> {

    @Override
    @Mapping(source = "subCategory.id", target = "subCategoryId")
    @Mapping(source = "superCategory.id", target = "superCategoryId")
    CategoryDto toDto(final Category category);

    @Override
    @Mapping(source = "subCategoryId", target = "subCategory.id")
    @Mapping(source = "superCategoryId", target = "superCategory.id")
    Category toEntity(final CategoryDto categoryDto);

}
