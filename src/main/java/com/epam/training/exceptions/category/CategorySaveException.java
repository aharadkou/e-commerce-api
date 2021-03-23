package com.epam.training.exceptions.category;

import com.epam.training.data.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST,
        reason = "Attempt to save category with invalid sub/super category or with same name!")
public class CategorySaveException extends RuntimeException{

    public CategorySaveException() {

    }

    public CategorySaveException(final Category category) {
        super("Received category:" + category);
    }

}
