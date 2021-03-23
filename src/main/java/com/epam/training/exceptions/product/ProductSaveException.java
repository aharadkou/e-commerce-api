package com.epam.training.exceptions.product;


import com.epam.training.data.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
        reason = "Attempt to save product with nonexistent category or with same name!")
public class ProductSaveException extends RuntimeException {

    public ProductSaveException() {

    }

    public ProductSaveException(final Product product) {
        super("Received product:" + product);
    }


}
