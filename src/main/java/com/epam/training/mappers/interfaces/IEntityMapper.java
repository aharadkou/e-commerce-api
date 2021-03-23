package com.epam.training.mappers.interfaces;

public interface IEntityMapper<E, DTO> {

    E toEntity(final DTO dto);

    DTO toDto(final E entity);

}
