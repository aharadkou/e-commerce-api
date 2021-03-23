package com.epam.training.mappers.impl;

import com.epam.training.exceptions.CurrencyInstantiationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Currency;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyMapperImplTest {

    private static final String USD_CURRENCY_CODE = "USD";

    private static final Currency USD = Currency.getInstance(USD_CURRENCY_CODE);

    private static final String INVALID_CURRENCY_CODE = "abcd";

    @Autowired
    private CurrencyMapperImpl currencyMapper;

    @Test
    public void asString() {
        //then
        Assert.assertEquals(currencyMapper.asString(USD), USD_CURRENCY_CODE);
    }

    @Test
    public void asCurrencyWithValidCode() {
        //then
        Assert.assertNotNull(currencyMapper.asCurrency(USD_CURRENCY_CODE));
    }

    @Test(expected = CurrencyInstantiationException.class)
    public void whenAsCurrencyWithInvalidCode_thenCurrencyInstantiationException() {
        currencyMapper.asCurrency(INVALID_CURRENCY_CODE);
    }

}
