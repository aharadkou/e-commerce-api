package com.epam.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Entity not found!")
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException() {

    }

    public EntityNotFoundException(final String searchParams) {
        super("Search params:" + searchParams);
    }
}
