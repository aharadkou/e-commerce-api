package com.epam.training.data;

import com.epam.training.data.randomizers.BigDecimalRandomizer;
import com.epam.training.data.randomizers.CurrencyRandomizer;
import io.github.benas.randombeans.annotation.Randomizer;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Currency;

@Data
@Embeddable
public class Price {

    @Randomizer(CurrencyRandomizer.class)
    private Currency currency;

    @EqualsAndHashCode.Exclude
    @Randomizer(BigDecimalRandomizer.class)
    private BigDecimal amount;

    @EqualsAndHashCode.Include
    private Object bigDecimalForEqHC() {
        return amount.stripTrailingZeros();
    }

}
