package com.epam.training.services.impl;

import com.epam.training.data.Category;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.category.CategorySaveException;
import com.epam.training.repositories.interfaces.ICategoryRepository;
import com.epam.training.services.interfaces.ICategoryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class CategoryServiceImpl extends AbstractCrudServiceImpl<Category> implements ICategoryService {

    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(final ICategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(final Category category) {
        try {
            setDetachedSubSuperCategories(category);
            return super.create(category);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex);
            throw new CategorySaveException(category);
        }
    }

    @Override
    public Category update(final Category category, final Long id) {
        try {
            setDetachedSubSuperCategories(category);
            return super.update(category, id);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex);
            throw new CategorySaveException(category);
        }
    }

    private void setDetachedSubSuperCategories(final Category category) {
        Category superCategory = category.getSuperCategory();
        Category subCategory = category.getSubCategory();
        checkCategoryForSelfReference(category, superCategory);
        checkCategoryForSelfReference(category, subCategory);
        checkCategoryForSelfReference(subCategory, superCategory);
        category.setSubCategory(getDetachedCategory(subCategory));
        category.setSuperCategory(getDetachedCategory(superCategory));
    }

    private void checkCategoryForSelfReference(final Category category, final Category subSuperCategory) {
        if (category != null && category.getId() != null && subSuperCategory != null) {
            if (category.getId().equals(subSuperCategory.getId())) {
                throw new CategorySaveException(category);
            }
        }
    }

    @Override
    public Category findByName(final String categoryName) {
        return categoryRepository.findByName(categoryName).orElseThrow(
                () -> new EntityNotFoundException("Category name : " + categoryName)
             );
    }
}
