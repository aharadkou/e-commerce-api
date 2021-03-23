package com.epam.training.facades.impl;

import com.epam.training.mappers.interfaces.IPriceMapper;
import com.epam.training.data.dto.PriceDto;
import com.epam.training.facades.interfaces.IPriceFacade;
import com.epam.training.services.interfaces.IPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceFacadeImpl implements IPriceFacade {

    private final IPriceService priceService;

    private final IPriceMapper priceMapper;

    @Autowired
    protected PriceFacadeImpl(final IPriceService priceService,
                              final IPriceMapper priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }

    @Override
    public PriceDto findByProductId(final Long productId) {
        return priceMapper.toDto(priceService.findByProductId(productId));
    }

    @Override
    public void deleteFromProduct(final Long productId) {
        priceService.deleteFromProduct(productId);
    }

    @Override
    public PriceDto addProductPrice(final PriceDto priceDto, final Long productId) {
        return priceMapper.toDto(
                priceService.addProductPrice(priceMapper.toEntity(priceDto), productId)
        );
    }

    @Override
    public PriceDto updateProductPrice(final PriceDto priceDto, Long productId) {
        return priceMapper.toDto(
                priceService.updateProductPrice(priceMapper.toEntity(priceDto), productId)
        );
    }
}
