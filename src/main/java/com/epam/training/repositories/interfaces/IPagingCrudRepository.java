package com.epam.training.repositories.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface IPagingCrudRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {

    Page<T> findAll(Pageable pageable);

}
