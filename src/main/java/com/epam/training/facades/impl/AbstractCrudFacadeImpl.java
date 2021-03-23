package com.epam.training.facades.impl;

import com.epam.training.mappers.interfaces.IEntityMapper;
import com.epam.training.facades.interfaces.ICrudFacade;
import com.epam.training.services.interfaces.ICrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract class AbstractCrudFacadeImpl<E, DTO> implements ICrudFacade<DTO> {

    private final IEntityMapper<E, DTO> entityMapper;

    private final ICrudService<E> crudService;

    protected AbstractCrudFacadeImpl(final ICrudService<E> crudService,
                                     final IEntityMapper<E, DTO> entityMapper) {
        this.entityMapper = entityMapper;
        this.crudService = crudService;
    }

    public Page<DTO> findAll(final Pageable pageable) {
        return toDtoPage(crudService.findAll(pageable));

    }

    public DTO findById(final Long id) {
        return entityMapper.toDto(crudService.findById(id));
    }

    public DTO save(final DTO dto) {
        return entityMapper.toDto(crudService.create(entityMapper.toEntity(dto)));
    }

    public DTO update(final DTO dto, final Long id) {
        return entityMapper.toDto(crudService.update(entityMapper.toEntity(dto), id));
    }

    public void deleteById(final Long entityId) {
        crudService.deleteById(entityId);
    }

    protected Page<DTO> toDtoPage(final Page<E> entities) {
        return entities
                    .map(entityMapper::toDto);
    }

}
