package com.epam.training.mappers.interfaces;

import java.util.Currency;

public interface ICurrencyMapper {

    String asString(final Currency currency);

    Currency asCurrency(final String currencyCode);

}
