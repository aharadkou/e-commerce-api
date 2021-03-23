package com.epam.training.services.interfaces;

import com.epam.training.data.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

public interface IProductService extends ICrudService<Product> {

    Product findByName(final String productName);

    Page<Product> findByPrice(final BigDecimal amount,
                              final Currency currency,
                              final Pageable pageable);

    Page<Product> findByPriceRange(final BigDecimal from,
                                   final BigDecimal to,
                                   final Currency currency,
                                   final Pageable pageable);

    Product findByCategoryId(final Long categoryId);
}
