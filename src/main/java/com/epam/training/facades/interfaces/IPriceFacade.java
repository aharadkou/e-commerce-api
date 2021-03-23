package com.epam.training.facades.interfaces;

import com.epam.training.data.dto.PriceDto;

public interface IPriceFacade {

    PriceDto findByProductId(final Long productId);

    void deleteFromProduct(final Long productId);

    PriceDto addProductPrice(final PriceDto priceDto, final Long productId);

    PriceDto updateProductPrice(final PriceDto priceDto, final Long productId);

}
