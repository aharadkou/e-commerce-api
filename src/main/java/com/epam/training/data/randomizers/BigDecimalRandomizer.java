package com.epam.training.data.randomizers;

import io.github.benas.randombeans.api.Randomizer;

import java.math.BigDecimal;
import java.util.Random;

public class BigDecimalRandomizer implements Randomizer<BigDecimal> {

    private static final Random RANDOM = new Random();

    private static final int MAX_VALUE = 100000;

    @Override
    public BigDecimal getRandomValue() {
        return BigDecimal.valueOf(RANDOM.nextInt(MAX_VALUE));
    }
}
