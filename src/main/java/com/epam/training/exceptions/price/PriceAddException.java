package com.epam.training.exceptions.price;

import com.epam.training.data.Price;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Product price already set!")
public class PriceAddException extends RuntimeException {

    public PriceAddException() {

    }

    public PriceAddException(final Price price, final Long productId) {
        super("Received price:" + price +
                System.lineSeparator() + "Received product id:" + productId);
    }

}
