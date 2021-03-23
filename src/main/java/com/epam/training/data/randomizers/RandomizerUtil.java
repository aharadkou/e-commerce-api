package com.epam.training.data.randomizers;

import com.github.rkumsher.collection.IterableUtils;

import java.util.Currency;

public class RandomizerUtil {

    private RandomizerUtil() {

    }

    public static Currency getRandomCurrency() {
        return IterableUtils
                    .randomFrom(Currency.getAvailableCurrencies());
    }

}
