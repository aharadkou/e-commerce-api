package com.epam.training.services.impl;

import com.epam.training.data.Category;
import com.epam.training.data.Product;
import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.category.CategorySaveException;
import com.epam.training.exceptions.product.ProductSaveException;
import com.epam.training.repositories.interfaces.IProductRepository;
import com.epam.training.services.interfaces.ICategoryService;
import com.epam.training.services.interfaces.IProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Currency;

@Log4j2
@Service
public class ProductServiceImpl extends AbstractCrudServiceImpl<Product> implements IProductService {

    private final IProductRepository productRepository;

    private final ICategoryService categoryService;

    @Autowired
    public ProductServiceImpl(final IProductRepository productRepository,
                              final ICategoryService categoryService) {
        super(productRepository);
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product create(final Product product) {
        try {
            setDetachedCategory(product);
            return super.create(product);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex);
            throw new ProductSaveException(product);
        }
    }

    @Override
    public Product update(final Product product, final Long id) {
        try {
            setDetachedCategory(product);
            return super.update(product, id);
        } catch (DataIntegrityViolationException ex) {
            log.error(ex);
            throw new ProductSaveException(product);
        }
    }

    private void setDetachedCategory(final Product product) {
        try {
            Category detachedCategory = categoryService.getDetachedCategory(product.getCategory());
            product.setCategory(detachedCategory);
        } catch (EntityNotFoundException ex) {
            throw new ProductSaveException(product);
        }
    }

    @Override
    public Product findByName(final String productName) {
        return productRepository.findByName(productName).orElseThrow(
                () -> new EntityNotFoundException("Product name : " + productName)
               );
    }

    @Override
    public Page<Product> findByPrice(final BigDecimal amount, final Currency currency,
                                     final Pageable pageable) {
        return productRepository.findByPrice_AmountAndPrice_Currency(amount, currency, pageable);
    }

    @Override
    public Page<Product> findByPriceRange(final BigDecimal from,
                                          final BigDecimal to,
                                          final Currency currency,
                                          final Pageable pageable) {
        return productRepository.findByPrice_AmountBetweenAndPrice_Currency(from, to, currency, pageable);
    }

    @Override
    public Product findByCategoryId(final Long categoryId) {
        return productRepository.findByCategoryId(categoryId).orElseThrow(
                () -> new EntityNotFoundException("Category id : " + categoryId)
        );
    }
}
