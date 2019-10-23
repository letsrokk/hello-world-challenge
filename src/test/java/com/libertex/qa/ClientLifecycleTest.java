package com.libertex.qa;

import com.libertex.qa.challenge.model.Client;
import com.libertex.qa.challenge.request.LoginRequest;
import com.libertex.qa.challenge.response.*;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ClientLifecycleTest {

    Client client = null;
    String sessionId = null;

    @BeforeAll
    public void prepareClientData() {
        this.client = Client.builder()
                .username(UUID.randomUUID().toString())
                .fullName(UUID.randomUUID().toString())
                .build();
    }

    @Order(1)
    @Test
    public void postClientTest() {
        StandartResponse receivedResponseBody = given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(this.client)
                    .post("/challenge/clients")
                .then()
                    .statusCode(200)
                .extract()
                    .body().as(StandartResponse.class);

        StandartResponse expectedResponseBody = StandartResponse.builder()
                .resultCode(ResultCode.Ok)
                .build();

        Assertions.assertEquals(receivedResponseBody, expectedResponseBody);
    }

    @Order(2)
    @Test
    public void getClientsTest() {
        GetClientsResponse receivedResponseBody = given()
                .when()
                    .get("/challenge/clients")
                .then()
                    .statusCode(200)
                .extract()
                    .body().as(GetClientsResponse.class);

        Assertions.assertTrue(receivedResponseBody.getClients().contains(this.client.getUsername()));
    }

    @Order(3)
    @Test
    public void loginTest() {
        LoginRequest requestBody =
                LoginRequest.builder()
                        .username(this.client.getUsername())
                        .build();

        this.sessionId = given()
                .when()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post("/challenge/login")
                .then()
                .statusCode(200)
                .extract()
                .header("X-Session-Id");

        Assertions.assertNotNull(sessionId);
    }

    @Order(4)
    @Test
    public void helloTest() {
        HelloResponse receivedResponseBody = given()
                .when()
                .header("X-Session-Id", this.sessionId)
                .get("/challenge/hello")
                .then()
                .statusCode(200)
                .extract()
                .body().as(HelloResponse.class);

        String expectedHelloMessage = String.format("Hello, %s!", this.client.getFullName());

        Assertions.assertEquals(receivedResponseBody.getMessage(), expectedHelloMessage);
    }
}