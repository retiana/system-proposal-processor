package com.ifglife;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class GreetingResourceTest {

    @Test
    void testSubmitProposalEndpoint() {
        String payload = """
                {
                  "proposalId": "PRO-001",
                  "customerId": "CUST-001",
                  "customerName": "John Doe",
                  "productCode": "LIFE-100",
                  "sumAssured": 500000000,
                  "age": 35,
                  "occupation": "Driver",
                  "annualIncome": 120000000
                }
                """;

        given()
                .contentType("application/json")
                .body(payload)
                .when()
                .post("/proposals")
                .then()
                .statusCode(202);
    }
}