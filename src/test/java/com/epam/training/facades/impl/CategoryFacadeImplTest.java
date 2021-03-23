package com.epam.training.facades.impl;

import com.epam.training.test.TestDataGeneratorUtil;
import com.epam.training.mappers.interfaces.ICategoryMapper;
import com.epam.training.data.Category;
import com.epam.training.data.dto.CategoryDto;
import com.epam.training.services.interfaces.ICategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryFacadeImplTest {

    @Autowired
    private CategoryFacadeImpl categoryFacade;

    @MockBean
    private ICategoryService categoryService;

    @SpyBean
    private ICategoryMapper categoryMapper;

    @Test
    public void findByName() {
        //given
        var category = TestDataGeneratorUtil.nextObject(Category.class);
        when(categoryService.findByName(anyString())).thenReturn(category);
        //when
        CategoryDto dto = categoryFacade.findByName(anyString());
        //then
        Assert.assertEquals(categoryMapper.toDto(category), dto);
    }


}
