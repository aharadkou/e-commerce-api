package com.epam.training.controllers;

import com.epam.training.mappers.interfaces.IProductMapper;
import com.epam.training.data.Product;
import com.epam.training.data.dto.ProductDto;
import com.epam.training.repositories.interfaces.IProductRepository;
import com.epam.training.test.TestDataGeneratorUtil;
import com.epam.training.test.TestInitializer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;


import java.math.BigDecimal;
import java.util.Currency;
import java.util.function.Predicate;

import static io.restassured.RestAssured.*;
import static com.epam.training.test.constants.PathConstants.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "security-enabled=false")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@Import(TestInitializer.class)
public class ProductControllerTest {

    @Autowired
    private TestInitializer testInitializer;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private IProductMapper productMapper;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }


    @Test
    public void findById() {
        //given
        var product = testInitializer.addNewProduct();
        //when
        var productDto = performGetSingleProductDto(PATH_PRODUCT_BY_ID, product.getId());
        //then
        Assert.assertEquals(productMapper.toDto(product), productDto);
        testInitializer.deleteProduct(product);
    }

    @Test
    public void findByName() {
        //given
        var product = testInitializer.addNewProduct();
        //when
        var productDto = performGetSingleProductDto(PATH_PRODUCT_NAME, product.getName());
        //then
        Assert.assertEquals(productMapper.toDto(product), productDto);
        testInitializer.deleteProduct(product);
    }

    @Test
    public void findByCategoryId() {
        //given
        var product = testInitializer.addNewProduct();
        //when
        var productDto = performGetSingleProductDto(PATH_PRODUCT_CATEGORY, product.getCategory().getId());
        //then
        Assert.assertEquals(productMapper.toDto(product), productDto);
        testInitializer.deleteProduct(product);
    }

    @Test
    public void findByPrice() {
        final int expectedTotalElements = 1;
        final int expectedTotalPages = 1;
        var product = testInitializer.addNewProduct();
        when()
                .get(PATH_PRODUCTS_PRICE, product.getPrice().getAmount(),
                     product.getPrice().getCurrency().getCurrencyCode()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .body(JSON_PAGE_TOTAL_ELEMENTS, equalTo(expectedTotalElements))
                .body(JSON_PAGE_TOTAL_PAGES, equalTo(expectedTotalPages))
                .body(JSON_CONTENT + "[0].id", equalTo(product.getId().intValue()));
        testInitializer.deleteProduct(product);
    }

    @Test
    public void findByPriceRange() {
        final BigDecimal from = BigDecimal.valueOf(100);
        final BigDecimal to = BigDecimal.valueOf(100000);
        final Currency usd = Currency.getInstance("USD");
        final int expectedTotalElements
                = (int) productRepository
                            .findAll(Pageable.unpaged()).stream()
                                .filter(isProductInRange(from, to, usd))
                                    .count();
        when()
                .get(PATH_PRODUCTS_PRICE_RANGE, from, to, usd.getCurrencyCode()).
        then()
                .log()
                .all()
                .body(JSON_PAGE_TOTAL_ELEMENTS, equalTo(expectedTotalElements));
    }

    private Predicate<Product> isProductInRange(final BigDecimal from,
                                                final BigDecimal to,
                                                final Currency currency) {
        return product -> product.getPrice().getAmount().compareTo(from) >= 0
                            && product.getPrice().getAmount().compareTo(to) <= 0
                                && product.getPrice().getCurrency().equals(currency);
    }

    private ProductDto performGetSingleProductDto(final String path,
                                                  final Object... params) {
        return when()
                        .get(path, params).
                then()
                        .statusCode(HttpStatus.OK.value())
                        .extract()
                        .as(ProductDto.class);
    }

    @Test
    public void create() {
        var expectedProduct = TestDataGeneratorUtil.nextObject(Product.class);
        var actualProduct =
                given()
                        .body(productMapper.toDto(expectedProduct))
                        .contentType(ContentType.JSON).
                when()
                        .post(PATH_PRODUCTS).
                then()
                        .log()
                        .all()
                        .body("id", notNullValue())
                        .statusCode(HttpStatus.CREATED.value())
                        .extract().as(ProductDto.class);
        expectedProduct.setId(actualProduct.getId());
        Assert.assertEquals(productMapper.toDto(expectedProduct), actualProduct);
        testInitializer.deleteProduct(expectedProduct);
    }

    @Test
    public void updateWithExistingId() {
        final String expectedName = "newName";
        var expectedProduct = testInitializer.addNewProduct();
        expectedProduct.setName(expectedName);
        var actualProduct =
        given()
                .body(productMapper.toDto(expectedProduct))
                .contentType(ContentType.JSON).
        when()
                .put(PATH_PRODUCT_BY_ID, expectedProduct.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .extract().as(ProductDto.class);
        Assert.assertEquals(productMapper.toDto(expectedProduct), actualProduct);
        testInitializer.deleteProduct(expectedProduct);
    }

    @Test
    public void delete() {
        var product = testInitializer.addNewProduct();
        when()
                .delete(PATH_PRODUCT_BY_ID, product.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
        Assert.assertFalse(productRepository.existsById(product.getId()));
    }

}
