package com.epam.training.controllers;

import com.epam.training.mappers.interfaces.IPriceMapper;
import com.epam.training.data.Product;
import com.epam.training.data.dto.PriceDto;
import com.epam.training.repositories.interfaces.IProductRepository;
import com.epam.training.test.TestDataGeneratorUtil;
import com.epam.training.test.TestInitializer;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
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
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static io.restassured.RestAssured.*;
import static com.epam.training.test.constants.PathConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT, properties = "security-enabled=false")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.HSQL)
@Import(TestInitializer.class)
public class PriceControllerTest {

    @LocalServerPort
    private int port;

    private Product product;

    @Autowired
    private TestInitializer testInitializer;

    @Autowired
    private IPriceMapper priceMapper;

    @Autowired
    private IProductRepository productRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        product = testInitializer.addNewProduct();
    }

    @After
    public void clearData() {
        testInitializer.deleteProduct(product);
    }

    @Test
    public void findByProductId() {
        ValidatableResponse response =
        when()
                .get(PATH_PRICE, product.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
        assertEqualsAmountAndCurrency(response, priceMapper.toDto(product.getPrice()));
    }

    @Test
    public void addPriceToProductWithoutPrice() {
        product.setPrice(null);
        productRepository.save(product);
        var priceDto = TestDataGeneratorUtil.nextObject(PriceDto.class);
        ValidatableResponse response =
        given()
                .body(priceDto)
                .contentType(ContentType.JSON).
        when()
                .post(PATH_PRICE, product.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value());
        assertEqualsAmountAndCurrency(response, priceDto);
    }

    @Test
    public void updateProductPrice() {
        var priceDto = TestDataGeneratorUtil.nextObject(PriceDto.class);
        ValidatableResponse response =
        given()
                .body(priceDto)
                .contentType(ContentType.JSON).
        when()
                .put(PATH_PRICE, product.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
        assertEqualsAmountAndCurrency(response, priceDto);
    }

    @Test
    public void deleteProductPrice() {
        when()
                .delete(PATH_PRICE, product.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
        Assert.assertNull(productRepository.findById(product.getId()).get()
                            .getPrice());
    }

    private void assertEqualsAmountAndCurrency(final ValidatableResponse response, final PriceDto price) {
        response.body("currency", equalTo(price.getCurrency()));
        double actualAmount = response.extract().body().jsonPath().getDouble("amount");
        Assert.assertEquals(Double.MIN_NORMAL, actualAmount, price.getAmount().doubleValue());
    }

}
