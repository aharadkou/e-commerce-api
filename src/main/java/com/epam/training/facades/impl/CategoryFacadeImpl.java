package com.epam.training.facades.impl;

import com.epam.training.mappers.interfaces.ICategoryMapper;
import com.epam.training.data.Category;
import com.epam.training.data.dto.CategoryDto;
import com.epam.training.facades.interfaces.ICategoryFacade;
import com.epam.training.services.interfaces.ICategoryService;
import org.springframework.stereotype.Component;

@Component
public class CategoryFacadeImpl extends AbstractCrudFacadeImpl<Category, CategoryDto> implements ICategoryFacade {

    private final ICategoryService categoryService;

    private final ICategoryMapper categoryMapper;

    protected CategoryFacadeImpl(final ICategoryService categoryService,
                                 final ICategoryMapper categoryMapper) {
        super(categoryService, categoryMapper);
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDto findByName(final String categoryName) {
        return categoryMapper.toDto(categoryService.findByName(categoryName));
    }
}
