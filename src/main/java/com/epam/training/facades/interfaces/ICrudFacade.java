package com.epam.training.facades.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICrudFacade<DTO> {

    Page<DTO> findAll(final Pageable pageable);

    DTO findById(final Long id);

    DTO save(final DTO dto);

    DTO update(final DTO dto, final Long id);

    void deleteById(final Long entityId);

}
