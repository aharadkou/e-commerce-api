package com.epam.training.exceptions;


import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Invalid page number!")
public class IllegalPageNumberException extends IllegalArgumentException {

    public IllegalPageNumberException() {

    }

    public IllegalPageNumberException(final Pageable pageable) {
        super("Received page request : "
                + System.lineSeparator() + pageable);
    }
}
