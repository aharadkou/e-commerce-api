package com.epam.training.services;

import com.epam.training.data.Category;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.category.CategorySaveException;
import com.epam.training.repositories.interfaces.ICategoryRepository;
import com.epam.training.services.impl.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    private static final Long NONEXISTENT_ID = -1L;

    @Autowired
    private CategoryServiceImpl categoryService;

    @MockBean
    private ICategoryRepository categoryRepository;

    private Category category;

    private Category nonexistentCategory;

    @Before
    public void setUp() {
        category = new Category();
        Category subCategory = new Category();
        subCategory.setId(Long.MAX_VALUE);
        category.setSubCategory(subCategory);
        Category superCategory = new Category();
        superCategory.setId(Long.MIN_VALUE);
        category.setSuperCategory(superCategory);
        nonexistentCategory = new Category();
        nonexistentCategory.setId(NONEXISTENT_ID);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.findById(NONEXISTENT_ID)).thenReturn(Optional.empty());
        when(categoryRepository.save(any())).thenReturn(category);
        when(categoryRepository.existsById(any())).thenReturn(true);
    }

    @Test
    public void findByExistingName() {
        //given
        when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        //when
        Category actual = categoryService.findByName("");
        //then
        Assert.assertEquals(category, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByNonexistentName() {
        when(categoryRepository.findByName(null)).thenReturn(Optional.empty());
        categoryService.findByName(null);
    }

    @Test
    public void createWithValidSubSuperCategory() {
        //when
        Category actual = categoryService.create(category);
        //then
        Assert.assertEquals(category, actual);
    }

    @Test(expected = CategorySaveException.class)
    public void whenCreateWithNonexistentSubSuperCategory_thenCategorySaveException() {
        category.setSubCategory(nonexistentCategory);
        category.setSuperCategory(nonexistentCategory);
        categoryService.create(category);
    }

    @Test(expected = CategorySaveException.class)
    public void whenCategoryRefersToItself_thenCategorySaveException() {
        category.setId(category.getSubCategory().getId());
        categoryService.update(category, Long.MAX_VALUE);
    }

    @Test(expected = CategorySaveException.class)
    public void whenCategorySubAndSuperCategoryIdAreEquals_thenCategorySaveException() {
        category.getSubCategory().setId(category.getSuperCategory().getId());
        categoryService.create(category);
    }

    @Test(expected = CategorySaveException.class)
    public void whenCategoryNameNotUnique_thenCategorySaveException() {
        Category categoryWithRepeatName = new Category();
        when(categoryRepository.save(categoryWithRepeatName)).thenThrow(DataIntegrityViolationException.class);
        categoryService.update(categoryWithRepeatName, Long.MAX_VALUE);
    }

    @Test
    public void updateWithValidSubSuperCategory() {
        //given
        Category actual = categoryService.update(category, Long.MAX_VALUE);
        //then
        Assert.assertEquals(category, actual);
    }

    @Test(expected = CategorySaveException.class)
    public void whenUpdateWithNonexistentSubSuperCategory_thenCategorySaveException() {
        category.setSubCategory(nonexistentCategory);
        category.setSuperCategory(nonexistentCategory);
        categoryService.update(category, Long.MAX_VALUE);
    }


}
