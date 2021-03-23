package com.epam.training.controllers;

import com.epam.training.mappers.interfaces.ICategoryMapper;
import com.epam.training.data.Category;
import com.epam.training.data.dto.CategoryDto;
import com.epam.training.repositories.interfaces.ICategoryRepository;
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
public class CategoryControllerTest {

    private static final String FIELD_ID = "id";

    private static final String FIELD_NAME = "name";

    @Autowired
    private TestInitializer testInitializer;

    @Autowired
    private ICategoryMapper categoryMapper;

    @Autowired
    private ICategoryRepository categoryRepository;

    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void findByIdWithValidId() {
        var expectedCategory = testInitializer.addNewCategory();
        performGetSingleCategoryAndCheckResult(expectedCategory,
                PATH_CATEGORY_BY_ID, expectedCategory.getId());
        testInitializer.deleteCategory(expectedCategory);
    }

    @Test
    public void findByNameWithExistingName() {
        var expectedCategory = testInitializer.addNewCategory();
        performGetSingleCategoryAndCheckResult(expectedCategory,
                PATH_FIND_CATEGORY_BY_NAME, expectedCategory.getName());
        testInitializer.deleteCategory(expectedCategory);
    }

    private void performGetSingleCategoryAndCheckResult(
            final Category expectedCategory,
            final String uri,
            final Object... paramValues) {
        var actualCategory =
                when()
                        .get(uri, paramValues).
                then()
                        .log()
                        .all()
                        .statusCode(HttpStatus.OK.value())
                        .extract().body().as(CategoryDto.class);
        Assert.assertEquals(categoryMapper.toDto(expectedCategory), actualCategory);
    }

    @Test
    public void create() {
        var category = TestDataGeneratorUtil.nextObject(Category.class);
        Long id =
        given()
                .body(categoryMapper.toDto(category))
                .contentType(ContentType.JSON).
        when()
                .post(PATH_CATEGORIES).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.CREATED.value())
                .body(FIELD_ID, notNullValue())
                .body(FIELD_NAME, equalTo(category.getName()))
                .extract().body().jsonPath().getLong(FIELD_ID);
        category.setId(id);
        testInitializer.deleteCategory(category);
    }

    @Test
    public void updateWithExistingId() {
        final String expectedName = "newName";
        var category = testInitializer.addNewCategory();
        category.setName(expectedName);
        given()
                .body(categoryMapper.toDto(category))
                .contentType(ContentType.JSON).
        when()
                .put(PATH_CATEGORY_BY_ID, category.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value())
                .body(FIELD_ID, equalTo(category.getId().intValue()))
                .body(FIELD_NAME, equalTo(expectedName));
        testInitializer.deleteCategory(category);
    }

    @Test
    public void delete() {
        var category = testInitializer.addNewCategory();
        when()
                .delete(PATH_CATEGORY_BY_ID, category.getId()).
        then()
                .log()
                .all()
                .statusCode(HttpStatus.OK.value());
        Assert.assertFalse(categoryRepository.existsById(category.getId()));
    }


    @Test
    public void findAllPagedWithValidPageNumber() {
        final int expectedPageNumber = 0;
        final int expectedPageSize = 10;
        final int expectedTotalPages = 10;
        when()
            .get(PATH_CATEGORIES_GET_ALL, expectedPageNumber, expectedPageSize)
        .then()
            .log()
            .all()
            .statusCode(HttpStatus.OK.value())
            .body(JSON_PAGE_SIZE, equalTo(expectedPageSize))
            .body(JSON_PAGE_TOTAL_ELEMENTS, equalTo(TestInitializer.INIT_COUNT))
            .body(JSON_PAGE_TOTAL_PAGES, equalTo(expectedTotalPages))
            .body(JSON_CONTENT, hasSize(expectedTotalPages));
    }


}
