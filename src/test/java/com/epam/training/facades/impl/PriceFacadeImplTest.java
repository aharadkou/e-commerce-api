package com.epam.training.facades.impl;

import com.epam.training.test.TestDataGeneratorUtil;
import com.epam.training.mappers.interfaces.IPriceMapper;
import com.epam.training.data.Price;
import com.epam.training.data.Product;
import com.epam.training.data.dto.PriceDto;
import com.epam.training.services.interfaces.IPriceService;
import org.junit.Assert;
import org.junit.Before;
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
public class PriceFacadeImplTest {

    @Autowired
    private PriceFacadeImpl priceFacade;

    @MockBean
    private IPriceService priceService;

    @SpyBean
    private IPriceMapper priceMapper;

    private Product product;

    @Before
    public void setUp() {
        product = TestDataGeneratorUtil.nextObject(Product.class);
    }

    @Test
    public void findByProductId() {
        //given
        when(priceService.findByProductId(anyLong()))
                .thenReturn(product.getPrice());
        //when
        PriceDto actual = priceFacade.findByProductId(anyLong());
        //then
        Assert.assertEquals(priceMapper.toDto(product.getPrice()), actual);

    }

    @Test
    public void deleteFromProduct() {
        //given
        doAnswer(invocationOnMock -> {
            product.setPrice(null);
            return null;
        }).when(priceService).deleteFromProduct(anyLong());
        //when
        priceFacade.deleteFromProduct(anyLong());
        //then
        Assert.assertNull(product.getPrice());
    }

    @Test
    public void addProductPrice() {
        //given
        when(priceService.addProductPrice(any(Price.class),anyLong()))
                .thenReturn(product.getPrice());
        //when
        PriceDto actual = priceFacade.addProductPrice(TestDataGeneratorUtil.nextObject(PriceDto.class), 0L);
        //then
        Assert.assertEquals(priceMapper.toDto(product.getPrice()), actual);

    }

    @Test
    public void updateProductPrice() {
        //given
        when(priceService.updateProductPrice(any(Price.class),anyLong()))
                .thenReturn(product.getPrice());
        //when
        PriceDto actual = priceFacade.updateProductPrice(TestDataGeneratorUtil.nextObject(PriceDto.class), 0L);
        //then
        Assert.assertEquals(priceMapper.toDto(product.getPrice()), actual);
    }


}
