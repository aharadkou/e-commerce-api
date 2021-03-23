package com.epam.training.services.impl;

import com.epam.training.data.Price;
import com.epam.training.data.Product;
import com.epam.training.exceptions.price.PriceAddException;
import com.epam.training.services.interfaces.IPriceService;
import com.epam.training.services.interfaces.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceServiceImpl implements IPriceService {

    private final IProductService productService;

    @Autowired
    public PriceServiceImpl(final IProductService productService) {
        this.productService = productService;
    }

    @Override
    public Price findByProductId(final Long productId) {
        Product product = productService.findById(productId);
        return product.getPrice();
    }

    @Override
    public void deleteFromProduct(final Long productId) {
        Product product = productService.findById(productId);
        product.setPrice(null);
        productService.update(product, productId);
    }

    @Override
    public Price addProductPrice(final Price price, final Long productId) {
        Product product = productService.findById(productId);
        if (product.getPrice() != null) {
            throw new PriceAddException(price, productId);
        }
        product.setPrice(price);
        productService.update(product, productId);
        return price;
    }

    @Override
    public Price updateProductPrice(final Price price, Long productId) {
        Product product = productService.findById(productId);
        product.setPrice(price);
        productService.update(product, productId);
        return price;
    }
}
