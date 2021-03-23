package com.epam.training.repositories.interfaces;

import com.epam.training.data.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends IPagingCrudRepository<Product, Long> {

    Optional<Product> findByName(final String productName);

    Page<Product> findByPrice_AmountAndPrice_Currency(final BigDecimal amount,
                                                      final Currency currency,
                                                      final Pageable pageable);

    Page<Product> findByPrice_AmountBetweenAndPrice_Currency(final BigDecimal from,
                                                             final BigDecimal to,
                                                             final Currency currency,
                                                             final Pageable pageable);

    Optional<Product> findByCategoryId(final Long categoryId);

}
