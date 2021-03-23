package com.epam.training.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICrudService<T> {

    Page<T> findAll(final Pageable pageable);

    T findById(final Long id);

    T create(final T entity);

    T update(final T entity, final Long id);

    void deleteById(final Long entityId);
}
