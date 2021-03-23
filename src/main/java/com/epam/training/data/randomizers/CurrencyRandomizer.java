package com.epam.training.data.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.util.Currency;

public class CurrencyRandomizer implements Randomizer<Currency> {

    @Override
    public Currency getRandomValue() {
        return RandomizerUtil.getRandomCurrency();
    }
}
