package com.epam.training.mappers.impl;

import com.epam.training.mappers.interfaces.ICategoryMapper;
import com.epam.training.data.Category;
import com.epam.training.data.dto.CategoryDto;
import com.epam.training.test.TestDataGeneratorUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperImplTest {

    @Autowired
    ICategoryMapper categoryMapper;

    private Category category;

    private CategoryDto categoryDto;

    @Before
    public void setUp() {
        category = TestDataGeneratorUtil.nextObject(Category.class);
        var superCategory = TestDataGeneratorUtil.nextObject(Category.class);
        var subCategory = TestDataGeneratorUtil.nextObject(Category.class);
        category.setSuperCategory(superCategory);
        category.setSubCategory(subCategory);
        categoryDto = categoryMapper.toDto(category);
    }

    @Test
    public void categoryToDto() {
        //then
        assertEqualsCategoryAndCategoryDto();
    }

    @Test
    public void dtoToCategory() {
        //when
        category = categoryMapper.toEntity(categoryDto);
        //then
        assertEqualsCategoryAndCategoryDto();
    }

    private void assertEqualsCategoryAndCategoryDto() {
        assertEqualsCategoryIdAndName(category, categoryDto);
        Assert.assertEquals(category.getSubCategory().getId(), categoryDto.getSubCategoryId());
        Assert.assertEquals(category.getSuperCategory().getId(), categoryDto.getSuperCategoryId());
    }

    private void assertEqualsCategoryIdAndName(final Category category, final CategoryDto categoryDto) {
        Assert.assertEquals(category.getId(), categoryDto.getId());
        Assert.assertEquals(category.getName(), categoryDto.getName());
    }

}
