package com.epam.training.services.interfaces;

import com.epam.training.data.Category;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.category.CategorySaveException;

import java.util.Optional;

public interface ICategoryService extends ICrudService<Category> {

    Category findByName(final String categoryName);

    default Category getDetachedCategory(final Category category) {
        try {
            if (category != null && category.getId() != null) {
                return findById(category.getId());
            }
            return null;
        } catch (EntityNotFoundException ex) {
            throw new CategorySaveException(category);
        }
    }

}
