package com.epam.training.repositories.interfaces;

import com.epam.training.data.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICategoryRepository extends IPagingCrudRepository<Category, Long> {

    Optional<Category> findByName(final String categoryName);
}
