package com.epam.training.data.dto;

import com.epam.training.data.randomizers.BigDecimalRandomizer;
import com.epam.training.data.randomizers.CurrencyStringRandomizer;
import io.github.benas.randombeans.annotation.Randomizer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
public class PriceDto {

    @NotBlank
    @Randomizer(CurrencyStringRandomizer.class)
    private String currency;

    @Min(0)
    @EqualsAndHashCode.Exclude
    @Randomizer(BigDecimalRandomizer.class)
    private BigDecimal amount;

    @EqualsAndHashCode.Include
    private Object bigDecimalForEqHC() {
        return amount.stripTrailingZeros();
    }

}
