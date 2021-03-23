package com.epam.training.facades.impl;


import com.epam.training.mappers.impl.CurrencyMapperImpl;
import com.epam.training.mappers.interfaces.ICurrencyMapper;
import com.epam.training.mappers.interfaces.IProductMapper;
import com.epam.training.data.Product;
import com.epam.training.data.dto.ProductDto;
import com.epam.training.facades.interfaces.IProductFacade;
import com.epam.training.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ProductFacadeImpl extends AbstractCrudFacadeImpl<Product, ProductDto> implements IProductFacade {

    private final IProductService productService;

    private final IProductMapper productMapper;

    private final ICurrencyMapper currencyMapper;

    @Autowired
    public ProductFacadeImpl(final IProductService productService,
                             final IProductMapper productMapper,
                             final ICurrencyMapper currencyMapper) {
        super(productService, productMapper);
        this.productService = productService;
        this.productMapper = productMapper;
        this.currencyMapper = currencyMapper;
    }


    @Override
    public ProductDto findByName(final String productName) {
        return productMapper.toDto(productService.findByName(productName));
    }

    @Override
    public Page<ProductDto> findByPrice(final BigDecimal amount,
                                        final String currency,
                                        final Pageable pageable) {
        return toDtoPage(
                productService.findByPrice(amount, currencyMapper.asCurrency(currency), pageable));

    }

    @Override
    public Page<ProductDto> findByPriceRange(final BigDecimal from,
                                             final BigDecimal to,
                                             final String currency,
                                             final Pageable pageable) {
        return toDtoPage(
                productService.findByPriceRange(from, to,
                        currencyMapper.asCurrency(currency), pageable));
    }

    @Override
    public ProductDto findByCategoryId(final Long categoryId) {
        return productMapper.toDto(productService.findByCategoryId(categoryId));
    }

}
