package com.epam.training.mappers.impl;

import com.epam.training.mappers.interfaces.IPriceMapper;
import com.epam.training.data.Price;
import com.epam.training.data.dto.PriceDto;
import com.epam.training.test.TestDataGeneratorUtil;
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
public class PriceMapperImplTest {

    @Autowired
    private IPriceMapper priceMapper;

    private Price price;

    private PriceDto priceDto;

    @Before
    public void setUp() {
        price = TestDataGeneratorUtil.nextObject(Price.class);
        priceDto = priceMapper.toDto(price);
    }

    @Test
    public void priceToDto() {
        //then
        assertEqualsPriceAndPriceDto();
    }

    @Test
    public void dtoToPrice() {
        //when
        price = priceMapper.toEntity(priceDto);
        //then
        assertEqualsPriceAndPriceDto();
    }

    private void assertEqualsPriceAndPriceDto() {
        Assert.assertEquals(price.getAmount(), priceDto.getAmount());
        Assert.assertEquals(price.getCurrency().getCurrencyCode(), priceDto.getCurrency());
    }


}
