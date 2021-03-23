package com.epam.training.validation;

import com.epam.training.exceptions.IllegalPageNumberException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class PageValidator {

    private static final int FIRST_PAGE = 0;

    public void validate(final Pageable pageable, final Page<?> page) {
        if (pageable.isPaged()) {
            if (pageable.getPageNumber() < FIRST_PAGE
                    || pageable.getPageNumber() > page.getTotalPages()) {
                throw new IllegalPageNumberException(pageable);
            }
        }
    }

}
