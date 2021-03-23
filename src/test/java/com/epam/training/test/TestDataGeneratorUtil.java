package com.epam.training.test;


import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

import java.util.List;
import java.util.stream.Collectors;

public class TestDataGeneratorUtil {

    private static final String[] EXCLUDED_FIELDS = {};

    private static final int SEED = 333;

    private static final int POOL_SIZE = 400;

    private static final int MIN_STR_LENGTH = 4;

    private static final int MAX_STR_LENGTH = 10;

    private static final EnhancedRandom enhancedRandom = EnhancedRandomBuilder
                                                            .aNewEnhancedRandomBuilder()
                                                            .seed(SEED)
                                                            .objectPoolSize(POOL_SIZE)
                                                            .stringLengthRange(MIN_STR_LENGTH, MAX_STR_LENGTH)
                                                            .build();

    public static <T> T nextObject(final Class<T> clazz) {
        return enhancedRandom.nextObject(clazz, EXCLUDED_FIELDS);
    }

    public static <T> List<T> objects(final Class<T> clazz, final int count) {
        return enhancedRandom
                .objects(clazz, count, EXCLUDED_FIELDS)
                    .collect(Collectors.toList());
    }


}
