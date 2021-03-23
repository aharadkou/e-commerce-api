package com.epam.training.facades.interfaces;

import com.epam.training.data.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface IProductFacade extends ICrudFacade<ProductDto> {

    ProductDto findByName(final String productName);

    Page<ProductDto> findByPrice(final BigDecimal amount,
                                 final String currency,
                                 final Pageable pageable);

    Page<ProductDto> findByPriceRange(final BigDecimal from,
                                      final BigDecimal to,
                                      final String currency,
                                      final Pageable pageable);

    ProductDto findByCategoryId(final Long categoryId);

}
