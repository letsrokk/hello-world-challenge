package com.libertex.qa;

import com.libertex.qa.challenge.model.Client;
import com.libertex.qa.challenge.response.ResultCode;
import com.libertex.qa.challenge.response.StandartResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ClientResourceTest {

    @Test
    public void testHelloEndpoint() {
        Client newClient = Client.builder()
                .username(UUID.randomUUID().toString())
                .fullName(UUID.randomUUID().toString())
                .build();

        StandartResponse expectedResponseBody = StandartResponse.builder()
                .resultCode(ResultCode.Ok)
                .build();

        StandartResponse receivedResponseBody = given()
                .when()
                    .contentType(ContentType.JSON)
                    .body(newClient)
                    .post("/challenge/clients")
                .then()
                    .statusCode(200)
                .extract()
                    .body().as(StandartResponse.class);

        Assertions.assertEquals(receivedResponseBody, expectedResponseBody);
    }

}