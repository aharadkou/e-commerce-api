package com.epam.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid currency code!")
public class CurrencyInstantiationException extends IllegalArgumentException {

    public CurrencyInstantiationException() {

    }

    public CurrencyInstantiationException(final String currencyCode) {
        super("Received code : " + currencyCode);
    }

}
