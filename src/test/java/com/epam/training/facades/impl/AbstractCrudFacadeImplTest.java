package com.epam.training.facades.impl;

import com.epam.training.test.TestDataGeneratorUtil;
import com.epam.training.mappers.interfaces.IEntityMapper;
import com.epam.training.data.Category;
import com.epam.training.data.dto.CategoryDto;
import com.epam.training.facades.interfaces.ICrudFacade;
import com.epam.training.services.interfaces.ICrudService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractCrudFacadeImplTest {

    @Mock
    ICrudService<Category> categoryCrudService;

    @Mock
    IEntityMapper<Category, CategoryDto> categoryMapper;

    private ICrudFacade<CategoryDto> categoryCrudFacade;

    private Category category;

    private CategoryDto categoryDto;

    private List<Category> categories;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        category = TestDataGeneratorUtil.nextObject(Category.class);
        categoryDto = new CategoryDto();
        categoryDto.setName(category.getName());
        categories = new ArrayList<>();
        categories.add(category);
        categoryCrudFacade = new AbstractCrudFacadeImpl<>(categoryCrudService, categoryMapper) {
        };
        when(categoryMapper.toDto(any(Category.class))).thenReturn(categoryDto);
        when(categoryMapper.toEntity(any(CategoryDto.class))).thenReturn(category);
     }

    @Test
    public void findAllPaged() {
        //given
        when(categoryCrudService.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(categories));
        //when
        Page<CategoryDto> actual = categoryCrudFacade.findAll(Pageable.unpaged());
        //then
        Assert.assertEquals(categoryDto, actual.get().findFirst().get());
    }

    @Test
    public void findById() {
        //given
        when(categoryCrudService.findById(anyLong())).thenReturn(category);
        //when
        CategoryDto actual = categoryCrudFacade.findById(Long.MAX_VALUE);
        //then
        Assert.assertEquals(categoryDto, actual);
    }

    @Test
    public void create() {
        //given
        when(categoryCrudService.create(any(Category.class))).thenReturn(category);
        //when
        CategoryDto actual = categoryCrudFacade.save(new CategoryDto());
        //then
        Assert.assertEquals(categoryDto, actual);
    }

    @Test
    public void update() {
        //given
        when(categoryCrudService.update(any(Category.class), anyLong())).thenReturn(category);
        //when
        CategoryDto actual = categoryCrudFacade.update(new CategoryDto(), 0L);
        //then
        Assert.assertEquals(categoryDto, actual);
    }

    @Test
    public void delete() {
        //given
        doAnswer(invocationOnMock -> {
            categories.remove(category);
            return null;
        }).when(categoryCrudService).deleteById(anyLong());
        //when
        categoryCrudFacade.deleteById(Long.MAX_VALUE);
        //then
        Assert.assertTrue(categories.isEmpty());
    }

}
