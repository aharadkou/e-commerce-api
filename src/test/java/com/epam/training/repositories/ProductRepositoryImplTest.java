package com.epam.training.repositories;

import com.epam.training.data.Product;
import com.epam.training.repositories.interfaces.IProductRepository;
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
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
public class ProductRepositoryImplTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private IProductRepository productRepository;


    @Test
    public void findAll() {
        //given
        final int expectedPageSize = 3;
        final int expectedTotalElements = 9;
        final int expectedTotalPages = 3;
        final int pageNumber = 0;
        for (int i = 0; i < expectedTotalElements; i++) {
            Product product = TestDataGeneratorUtil.nextObject(Product.class);
            entityManager.persist(product.getCategory());
            entityManager.persist(product);
        }
        //when
        Page<Product> products = productRepository.findAll(PageRequest.of(pageNumber, expectedPageSize));
        //then
        Assert.assertEquals(expectedPageSize, products.getSize());
        Assert.assertEquals(expectedTotalElements, products.getTotalElements());
        Assert.assertEquals(expectedTotalPages, products.getTotalPages());
        Assert.assertEquals(pageNumber, products.getNumber());
    }

    @Test
    public void findByExistingName() {
        //given
        Product expected = TestDataGeneratorUtil.nextObject(Product.class);
        entityManager.persist(expected.getCategory());
        entityManager.persist(expected);
        //when
        Product actual = productRepository.findByName(expected.getName()).get();
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByNonexistentName() {
        //given
        final String nonexistentName = "ffff";
        //when
        Optional<Product> actual = productRepository.findByName(nonexistentName);
        //then
        Assert.assertFalse(actual.isPresent());
    }

    @Test
    public void findByExistingCategoryId() {
        //given
        Product expected = TestDataGeneratorUtil.nextObject(Product.class);
        entityManager.persist(expected.getCategory());
        entityManager.persist(expected);
        //when
        Product actual = productRepository.findByCategoryId(expected.getCategory().getId()).get();
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void findByNonexistentCategoryId() {
        //when
        Optional<Product> actual = productRepository.findByCategoryId(Long.MIN_VALUE);
        //then
        Assert.assertFalse(actual.isPresent());
    }

    @Test
    public void findByPrice_AmountAndPrice_Currency() {
        //given
        final int expectedPageSize = 1;
        final int expectedTotalElements = 1;
        final int pageNumber = 0;
        Product product = TestDataGeneratorUtil.nextObject(Product.class);
        entityManager.persist(product.getCategory());
        entityManager.persist(product);
        //when
        Page<Product> products = productRepository.findByPrice_AmountAndPrice_Currency(
                product.getPrice().getAmount(), product.getPrice().getCurrency(),
                PageRequest.of(pageNumber, expectedPageSize));
        //then
        Assert.assertEquals(product, products.get().findFirst().get());
        Assert.assertEquals(expectedPageSize, products.getSize());
        Assert.assertEquals(expectedTotalElements, products.getTotalElements());
        Assert.assertEquals(pageNumber, products.getNumber());
    }

    @Test
    public void findByPrice_AmountBetweenAndPrice_Currency() {
        //given
        final int addedProductsCount = 20;

        final int expectedTotalElements = 10;
        final int expectedTotalPages = 2;
        final int pageNumber = 0;
        final int pageSize = 5;
        final BigDecimal from = BigDecimal.ZERO;
        final BigDecimal to = BigDecimal.valueOf(9);
        final Currency usd = Currency.getInstance("USD");
        for (int i = 0; i < addedProductsCount; i++) {
            Product product = TestDataGeneratorUtil.nextObject(Product.class);
            entityManager.persist(product.getCategory());
            product.getPrice().setAmount(BigDecimal.valueOf(i));
            product.getPrice().setCurrency(usd);
            entityManager.persist(product);
        }
        //when
        Page<Product> products = productRepository.findByPrice_AmountBetweenAndPrice_Currency(
                from, to, usd, PageRequest.of(pageNumber, pageSize));
        //then
        Assert.assertEquals(expectedTotalElements, products.getTotalElements());
        Assert.assertEquals(expectedTotalPages, products.getTotalPages());
    }


}
