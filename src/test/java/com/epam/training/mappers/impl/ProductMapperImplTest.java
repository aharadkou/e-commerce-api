package com.epam.training.mappers.impl;

import com.epam.training.mappers.interfaces.IProductMapper;
import com.epam.training.data.Category;
import com.epam.training.data.Price;
import com.epam.training.data.Product;
import com.epam.training.data.dto.ProductDto;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Currency;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductMapperImplTest {

    @Autowired
    private IProductMapper productMapper;

    private Product product;

    private ProductDto productDto;

    @Before
    public void setUp() {
        product = new Product();
        final Long id = 1L;
        product.setId(id);
        product.setName("productName");
        product.setDescription("description");
        var category = new Category();
        category.setId(id);
        category.setName("productName");
        category.setSubCategory(category);
        category.setSuperCategory(category);
        product.setCategory(category);
        var price = new Price();
        price.setAmount(BigDecimal.TEN);
        price.setCurrency(Currency.getInstance("USD"));
        product.setPrice(price);
        productDto = productMapper.toDto(product);
    }

    @Test
    public void productToDto() {
        //then
        assertEqualsProductAndProductDto();
    }

    @Test
    public void dtoToProduct() {
        //when
        product = productMapper.toEntity(productDto);
        //then
        assertEqualsProductAndProductDto();
    }

    private void assertEqualsProductAndProductDto() {
        Assert.assertEquals(product.getId(), productDto.getId());
        Assert.assertEquals(product.getName(), productDto.getName());
        Assert.assertEquals(product.getCategory().getId(), productDto.getCategoryId());
        Assert.assertEquals(product.getDescription(), productDto.getDescription());
        Assert.assertEquals(product.getPrice().getCurrency().getCurrencyCode(), productDto.getPrice().getCurrency());
        Assert.assertEquals(product.getPrice().getAmount(), productDto.getPrice().getAmount());
    }

}
