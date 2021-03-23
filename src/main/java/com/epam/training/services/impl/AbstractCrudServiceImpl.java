package com.epam.training.services.impl;

import com.epam.training.exceptions.EntityNotFoundException;
import com.epam.training.exceptions.UpdateNonexistentEntityException;
import com.epam.training.repositories.interfaces.IPagingCrudRepository;
import com.epam.training.services.interfaces.ICrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public abstract class AbstractCrudServiceImpl<T> implements ICrudService<T> {

    private IPagingCrudRepository<T, Long> crudRepository;

    protected AbstractCrudServiceImpl(final IPagingCrudRepository<T, Long> crudRepository) {
        this.crudRepository = crudRepository;
    }

    public Page<T> findAll(final Pageable pageable) {
        return crudRepository.findAll(pageable);
    }

    public T findById(final Long id) {
        return crudRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Id : " + id));
    }

    public T create(final T entity) {
        return crudRepository.save(entity);
    }

    public T update(final T entity, final Long id) {
        if (!crudRepository.existsById(id)) {
            throw new UpdateNonexistentEntityException(entity, id);
        }
        return crudRepository.save(entity);
    }

    public void deleteById(final Long entityId) {
        crudRepository.deleteById(entityId);
    }


}
