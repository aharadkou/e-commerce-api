package com.epam.training.services.interfaces;

import com.epam.training.data.Price;

import java.io.Serializable;

public interface IPriceService {

    Price findByProductId(final Long productId);

    void deleteFromProduct(final Long productId);

    Price addProductPrice(final Price price, final Long productId);

    Price updateProductPrice(final Price price, final Long productId);

}
