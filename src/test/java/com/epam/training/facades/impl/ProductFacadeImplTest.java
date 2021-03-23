package com.epam.training.facades.impl;


import com.epam.training.data.Product;
import com.epam.training.data.dto.ProductDto;
import com.epam.training.facades.interfaces.IProductFacade;
import com.epam.training.mappers.interfaces.ICurrencyMapper;
import com.epam.training.mappers.interfaces.IProductMapper;
import com.epam.training.services.interfaces.IProductService;
import com.epam.training.test.TestDataGeneratorUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductFacadeImplTest {

    @SpyBean
    private IProductMapper productMapper;

    @SpyBean
    private ICurrencyMapper currencyMapper;

    @MockBean
    private IProductService productService;

    @Autowired
    private IProductFacade productFacade;

    private Product product;

    private Page<Product> products;

    @Before
    public void setUp() {
        product = TestDataGeneratorUtil.nextObject(Product.class);
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        products = new PageImpl<>(productList);
    }

    @Test
    public void findByName() {
        //given
        when(productService.findByName(anyString())).thenReturn(product);
        //when
        ProductDto dto = productFacade.findByName("");
        //then
        Assert.assertEquals(productMapper.toDto(product), dto);
    }

    @Test
    public void findByCategoryId() {
        //given
        when(productService.findByCategoryId(anyLong())).thenReturn(product);
        //when
        ProductDto dto = productFacade.findByCategoryId(Long.MAX_VALUE);
        //then
        Assert.assertEquals(productMapper.toDto(product), dto);
    }

    @Test
    public void findByPrice() {
        //given
        when(productService.findByPrice(any(), any(), any())).thenReturn(products);
        //when
        Page<ProductDto> actualProducts = productFacade.findByPrice(BigDecimal.ZERO, "USD", Pageable.unpaged());
        //then
        Assert.assertEquals(productMapper.toDto(product), actualProducts.get().findFirst().get());
    }

    @Test
    public void findByPriceRange() {
        //given
        when(productService.findByPriceRange(any(), any(), any(), any())).thenReturn(products);
        //when
        Page<ProductDto> actualProducts = productFacade.findByPriceRange(BigDecimal.ZERO, BigDecimal.ZERO,
                "USD", Pageable.unpaged());
        //then
        Assert.assertEquals(productMapper.toDto(product), actualProducts.get().findFirst().get());
    }

}
