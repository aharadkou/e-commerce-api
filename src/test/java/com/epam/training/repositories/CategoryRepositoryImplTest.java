package com.epam.training.repositories;

import com.epam.training.data.Category;
import com.epam.training.repositories.interfaces.ICategoryRepository;
import com.epam.training.test.TestDataGeneratorUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class CategoryRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ICategoryRepository categoryRepository;

    @Test
    public void findByExistingName() {
        //given
        final String categoryName = "new";
        var category = new Category();
        category.setName(categoryName);
        entityManager.persist(category);
        //when
        var actualCategory = categoryRepository.findByName(categoryName);
        //then
        Assert.assertNotNull(actualCategory.get().getId());
        Assert.assertEquals(actualCategory.get().getName(), categoryName);
    }

    @Test
    public void findByNonexistentName() {
        //when
        var actualCategory = categoryRepository.findByName("nonexistent");
        //then
        Assert.assertFalse(actualCategory.isPresent());
    }

    @Test
    public void findAll() {
        //given
        final int expectedPageSize = 3;
        final int expectedTotalElements = 9;
        final int expectedTotalPages = 3;
        final int pageNumber = 0;
        for (int i = 0; i < expectedTotalElements; i++) {
            Category category = TestDataGeneratorUtil.nextObject(Category.class);
            entityManager.persist(category);
        }
        //when
        Page<Category> categories = categoryRepository.findAll(PageRequest.of(pageNumber, expectedPageSize));
        //then
        Assert.assertEquals(expectedPageSize, categories.getSize());
        Assert.assertEquals(expectedTotalElements, categories.getTotalElements());
        Assert.assertEquals(expectedTotalPages, categories.getTotalPages());
        Assert.assertEquals(pageNumber, categories.getNumber());
    }

}
