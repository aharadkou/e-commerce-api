package com.epam.training.mappers.impl;

import com.epam.training.mappers.interfaces.ICurrencyMapper;
import com.epam.training.exceptions.CurrencyInstantiationException;
import org.mapstruct.Mapper;

import java.util.Currency;

@Mapper(componentModel = "spring")
public class CurrencyMapperImpl implements ICurrencyMapper {

    public String asString(final Currency currency) {
        return currency.getCurrencyCode();
    }

    public Currency asCurrency(final String currencyCode) {
        try {
            return Currency.getInstance(currencyCode);
        }  catch (IllegalArgumentException ex) {
            throw new CurrencyInstantiationException(currencyCode);
        }
    }
}
