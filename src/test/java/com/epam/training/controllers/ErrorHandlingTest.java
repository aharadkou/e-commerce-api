package com.epam.training.controllers;

import com.epam.training.data.dto.CategoryDto;
import com.epam.training.data.dto.PriceDto;
import com.epam.training.data.dto.ProductDto;
import com.epam.training.test.TestDataGeneratorUtil;
import com.epam.training.test.TestInitializer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static io.restassured.RestAssured.*;
import static com.epam.training.test.constants.PathConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "security-enabled=false")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@Import(TestInitializer.class)
public class ErrorHandlingTest {

    private static final Long INVALID_ID = Long.MAX_VALUE;

    @LocalServerPort
    private int port;

    @Autowired
    private TestInitializer testInitializer;

    private CategoryDto invalidCategory;

    private ProductDto invalidProduct;

    @Before
    public void setUp() {
        RestAssured.port = port;
        invalidCategory = TestDataGeneratorUtil.nextObject(CategoryDto.class);
        invalidCategory.setSubCategoryId(Long.MAX_VALUE);
        invalidProduct = TestDataGeneratorUtil.nextObject(ProductDto.class);
    }

    @Test
    public void createProductWithEmptyProductName() {
        invalidProduct.setName("");
        postResourceAndAssertBadRequestStatusCode(invalidProduct, PATH_PRODUCTS);
    }

    @Test
    public void createProductWithInvalidPriceCurrency() {
        invalidProduct.getPrice().setCurrency("invalidCurrency");
        postResourceAndAssertBadRequestStatusCode(invalidProduct, PATH_PRODUCTS);
    }

    @Test
    public void createProductWithNegativePriceAmount() {
        invalidProduct.getPrice().setAmount(BigDecimal.TEN.negate());
        postResourceAndAssertBadRequestStatusCode(invalidProduct, PATH_PRODUCTS);
    }

    @Test
    public void createProductWithInvalidCategoryId() {
        invalidProduct.setCategoryId(Long.MAX_VALUE);
        postResourceAndAssertBadRequestStatusCode(invalidProduct, PATH_PRODUCTS);
    }

    @Test
    public void createCategoryWithEmptyName() {
        invalidCategory.setName("");
        postResourceAndAssertBadRequestStatusCode(invalidCategory, PATH_CATEGORIES);
    }

    @Test
    public void createCategoryWithInvalidSubCategoryId() {
        invalidCategory.setSubCategoryId(Long.MAX_VALUE);
        postResourceAndAssertBadRequestStatusCode(invalidCategory, PATH_CATEGORIES);
    }

    @Test
    public void addNewPriceToProductWithSetPrice() {
        var newPrice = TestDataGeneratorUtil.nextObject(PriceDto.class);
        var productWithSetPrice = testInitializer.addNewProduct();
        postResourceAndAssertBadRequestStatusCode(newPrice, PATH_PRICE, productWithSetPrice.getId());
        testInitializer.deleteProduct(productWithSetPrice);
    }


    private void postResourceAndAssertBadRequestStatusCode(final Object body,
                                                           final String path,
                                                           final Object... params) {
        given()
                .body(body)
                .contentType(ContentType.JSON).
        when()
                .post(path, params).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void getAllCategoriesPagedWithInvalidPageNumber() {
        final int invalidPageNumber = 200;
        final int pageSize = 10;
        getResourceAndAssertNotFoundResponseStatus(PATH_CATEGORIES_GET_ALL, invalidPageNumber, pageSize);
        getResourceAndAssertNotFoundResponseStatus(PATH_PRODUCTS_GET_ALL, invalidPageNumber, pageSize);
    }

    @Test
    public void findByIdWithNonexistentId() {
        getResourceAndAssertNotFoundResponseStatus(PATH_PRODUCT_BY_ID, INVALID_ID);
        getResourceAndAssertNotFoundResponseStatus(PATH_CATEGORY_BY_ID, INVALID_ID);
    }


    private void getResourceAndAssertNotFoundResponseStatus(final String path, final Object... params) {
        when()
                .get(path, params).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void updateNonexistentCategoryAndProduct() {
        invalidCategory.setId(INVALID_ID);
        invalidProduct.setId(INVALID_ID);
        putResourceAndAssertBadRequestStatusCode(invalidCategory, PATH_CATEGORY_BY_ID, INVALID_ID);
        putResourceAndAssertBadRequestStatusCode(invalidProduct, PATH_PRODUCT_BY_ID, INVALID_ID);
    }

    private void putResourceAndAssertBadRequestStatusCode(final Object body,
                                                          final String path,
                                                          final Object... params) {
        given()
                .body(body)
                .contentType(ContentType.JSON).
        when()
                .put(path, params).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

}
