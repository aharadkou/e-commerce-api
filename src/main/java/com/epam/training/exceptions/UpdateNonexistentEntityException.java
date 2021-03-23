package com.epam.training.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Attempt to update nonexistent entity!")
public class UpdateNonexistentEntityException extends IllegalArgumentException {

    public UpdateNonexistentEntityException() {

    }

    public UpdateNonexistentEntityException(final Object receivedEntity, final Long entityId) {
        super("Received entity: " + receivedEntity
                + System.lineSeparator() + "Received entity id:" + entityId );
    }
}
