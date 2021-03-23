package com.epam.training.services;

import com.epam.training.data.Category;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.UpdateNonexistentEntityException;
import com.epam.training.repositories.interfaces.IPagingCrudRepository;
import com.epam.training.services.impl.AbstractCrudServiceImpl;
import com.epam.training.services.interfaces.ICrudService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AbstractCrudServiceImplTest {

    private ICrudService<Category> categoryService;

    @Mock
    private IPagingCrudRepository<Category, Long> categoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        categoryService = new AbstractCrudServiceImpl<>(categoryRepository) {
        };
    }

    @Test
    public void findAll() {
        //given
        Page<Category> expected = Page.empty();
        when(categoryRepository.findAll(any())).thenReturn(expected);
        //when
        Page<Category> actual = categoryService.findAll(Pageable.unpaged());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findExistingById() {
        //given
        Category expected = new Category();
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        //when
        Category actual = categoryService.findById(Long.MAX_VALUE);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenFindNonexistentById_thenEntityNotFoundException() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());
        categoryService.findById(Long.MAX_VALUE);
    }

    @Test
    public void create() {
        //given
        Category expected = new Category();
        when(categoryRepository.save(any())).thenReturn(expected);
        //when
        Category actual = categoryService.create(new Category());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void updateExisting() {
        //given
        Category expected = new Category();
        when(categoryRepository.save(any())).thenReturn(expected);
        when(categoryRepository.existsById(anyLong())).thenReturn(true);
        //when
        Category actual = categoryService.update(new Category(), Long.MAX_VALUE);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UpdateNonexistentEntityException.class)
    public void whenUpdateNonexistent_thenUpdateNonexistentEntityException() {
        when(categoryRepository.existsById(anyLong())).thenReturn(false);
        categoryService.update(new Category(), Long.MAX_VALUE);
    }

    @Test
    public void deleteById() {
        //given
        List<Category> categories = new ArrayList<>(Collections.singletonList(new Category()));
        doAnswer(invocationOnMock -> {
            categories.remove(0);
            return null;
        }).when(categoryRepository).deleteById(anyLong());
        //when
        categoryService.deleteById(Long.MAX_VALUE);
        //then
        Assert.assertTrue(categories.isEmpty());
    }

}
