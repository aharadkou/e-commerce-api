package com.epam.training.data.randomizers;


import io.github.benas.randombeans.api.Randomizer;


public class CurrencyStringRandomizer implements Randomizer<String> {

    @Override
    public String getRandomValue() {
        return RandomizerUtil
                .getRandomCurrency()
                    .getCurrencyCode();
    }
}
