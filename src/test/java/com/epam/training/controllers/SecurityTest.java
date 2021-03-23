package com.epam.training.controllers;

import com.epam.training.test.TestInitializer;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static io.restassured.RestAssured.*;
import static com.epam.training.test.constants.PathConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Import(TestInitializer.class)
public class SecurityTest {

    @Value("${admin.userName}")
    private String admin;

    @Value("${admin.password}")
    private String adminPassword;


    @LocalServerPort
    private int port;

    @Before
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void sendRequestWithSendingCredentialsForBasicAuth() {
        given()
                .auth()
                .basic(admin, adminPassword).
        when()
                .get(PATH_PRODUCTS).
        then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void sendRequestWithoutAuth() {
        when()
            .get(PATH_PRODUCTS).
        then()
            .statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
