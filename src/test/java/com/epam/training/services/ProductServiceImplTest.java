package com.epam.training.services;

import com.epam.training.data.Category;
import com.epam.training.data.Product;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.category.CategorySaveException;
import com.epam.training.exceptions.product.ProductSaveException;
import com.epam.training.repositories.interfaces.IProductRepository;
import com.epam.training.services.impl.ProductServiceImpl;
import com.epam.training.services.interfaces.ICategoryService;
import com.epam.training.test.TestDataGeneratorUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

import static org.mockito.Mockito.*;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    IProductRepository productRepository;

    @MockBean
    ICategoryService categoryService;

    private Product product;

    @Before
    public void setUp() {
        product = TestDataGeneratorUtil.nextObject(Product.class);
        product.getCategory().setId(Long.MAX_VALUE);
        when(categoryService.getDetachedCategory(any())).thenReturn(product.getCategory());
        when(categoryService.getDetachedCategory(null)).thenThrow(EntityNotFoundException.class);
        when(productRepository.save(any())).thenReturn(product);
        when(productRepository.existsById(anyLong())).thenReturn(true);
    }

    @Test
    public void createWithExistingCategoryId() {
        //when
        Product actual = productService.create(product);
        //then
        Assert.assertEquals(product.getCategory(), actual.getCategory());
    }

    @Test(expected = ProductSaveException.class)
    public void whenCreateWithNonexistentCategoryId_thenProductSaveException() {
        //given
        product.setCategory(null);
        //when
        productService.create(product);
    }

    @Test
    public void updateWithExistingCategoryId() {
        //when
        Product actual = productService.update(product, Long.MAX_VALUE);
        //then
        Assert.assertEquals(product.getCategory(), actual.getCategory());
    }

    @Test(expected = ProductSaveException.class)
    public void whenUpdateWithNonexistentCategoryId_thenProductSaveException() {
        //given
        product.setCategory(null);
        //when
        productService.update(product, Long.MAX_VALUE);
    }

    @Test
    public void findByExistingName() {
        //given
        when(productRepository.findByName(anyString())).thenReturn(Optional.of(product));
        //when
        Product actual = productService.findByName(product.getName());
        //then
        Assert.assertEquals(product, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenFindByNonexistentName_thenEntityNotFoundException() {
        when(productRepository.findByName(null)).thenReturn(Optional.empty());
        productService.findByName(null);
    }

    @Test(expected = ProductSaveException.class)
    public void whenProductNameNotUnique_thenProductSaveException() {
        Product productWithRepeatName = new Product();
        when(productRepository.save(productWithRepeatName)).thenThrow(DataIntegrityViolationException.class);
        productService.update(productWithRepeatName, Long.MAX_VALUE);
    }

    @Test
    public void findByExistingCategoryId() {
        //given
        when(productRepository.findByCategoryId(anyLong())).thenReturn(Optional.of(product));
        //when
        Product actual = productService.findByCategoryId(Long.MAX_VALUE);
        //then
        Assert.assertEquals(product, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenFindByNonexistentCategoryId_thenEntityNotFoundException() {
        when(productRepository.findByCategoryId(null)).thenReturn(Optional.empty());
        productService.findByCategoryId(null);
    }


    @Test
    public void findByPrice() {
        //given
        Page<Product> expected = Page.empty();
        when(productRepository.findByPrice_AmountAndPrice_Currency(any(), any(), any())).thenReturn(expected);
        //when
        Page<Product> actual  = productService.findByPrice(BigDecimal.ZERO, Currency.getInstance("USD"),
                                                    Pageable.unpaged());
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByPriceRange() {
        //given
        Page<Product> expected = Page.empty();
        when(productRepository
                .findByPrice_AmountBetweenAndPrice_Currency(any(), any(), any(), any()))
                                .thenReturn(expected);
        //when
        Page<Product> actual  = productService.findByPriceRange(BigDecimal.ZERO, BigDecimal.ZERO,
                Currency.getInstance("USD"), Pageable.unpaged());
        //then
        Assert.assertEquals(expected, actual);
    }


}
