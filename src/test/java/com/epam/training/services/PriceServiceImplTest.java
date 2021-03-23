package com.epam.training.services;

import com.epam.training.data.Price;
import com.epam.training.data.Product;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.price.PriceAddException;
import com.epam.training.services.impl.PriceServiceImpl;
import com.epam.training.services.interfaces.IProductService;
import com.epam.training.test.TestDataGeneratorUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class PriceServiceImplTest {

    @MockBean
    private IProductService productService;

    @Autowired
    private PriceServiceImpl priceService;

    private Product product;

    @Before
    public void setUp() {
        product = TestDataGeneratorUtil.nextObject(Product.class);
        when(productService.findById(anyLong())).thenReturn(product);
        when(productService.update(any(), anyLong())).thenReturn(product);
    }

    @Test
    public void findByProductId() {
        //when
        Price actual = priceService.findByProductId(Long.MAX_VALUE);
        //then
        Assert.assertEquals(product.getPrice(), actual);
    }

    @Test
    public void deleteFromProduct() {
        //when
        priceService.deleteFromProduct(Long.MAX_VALUE);
        //then
        Assert.assertNull(product.getPrice());
    }

    @Test
    public void addProductPrice() {
        //given
        Price expected = TestDataGeneratorUtil.nextObject(Price.class);
        product.setPrice(null);
        //when
        Price actual = priceService.addProductPrice(expected, Long.MAX_VALUE);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = PriceAddException.class)
    public void whenAddPriceToProductWithAlreadySetPrice_thenPriceAddException() {
        priceService.addProductPrice(new Price(), Long.MAX_VALUE);
    }

    @Test
    public void updateProductPrice() {
        //given
        Price expected = TestDataGeneratorUtil.nextObject(Price.class);
        //when
        Price actual = priceService.updateProductPrice(expected, Long.MAX_VALUE);
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = EntityNotFoundException.class)
    public void whenPerformOperationWithPriceOnNonexistentProduct_thenEntityNotFoundException() {
        when(productService.findById(null)).thenThrow(EntityNotFoundException.class);
        priceService.deleteFromProduct(null);
    }
}
