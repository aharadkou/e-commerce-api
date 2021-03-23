package com.epam.training.facades.interfaces;

import com.epam.training.data.dto.CategoryDto;

public interface ICategoryFacade extends ICrudFacade<CategoryDto> {

    CategoryDto findByName(final String categoryName);

}
