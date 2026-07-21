package com.ifglife.controller;

import com.ifglife.profile.CrudDbTestProfile;
import com.ifglife.repository.PolicyProposalRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@QuarkusTest
@TestProfile(CrudDbTestProfile.class)
class ProposalCrudIntegrationTest {

    @Inject
    PolicyProposalRepository policyProposalRepository;

    @BeforeEach
    @Transactional
    void clearData() {
        policyProposalRepository.deleteAll();
    }

    @Test
    void shouldPerformCrudLifecycle() {
        Map<String, Object> createPayload = Map.of(
                "proposalId", "PRO-CRUD-001",
                "customerId", "CUST-001",
                "customerName", "Budi",
                "productCode", "LIFE-100",
                "age", 35,
                "occupation", "Teacher"
        );

        given()
                .contentType("application/json")
                .body(createPayload)
                .when()
                .post("/proposals")
                .then()
                .statusCode(201)
                .body("proposalId", equalTo("PRO-CRUD-001"))
                .body("underwritingStatus", equalTo("APPROVED"));

        given()
                .when()
                .get("/proposals/PRO-CRUD-001")
                .then()
                .statusCode(200)
                .body("customerName", equalTo("Budi"))
                .body("riskScore", equalTo(30.0F));

        Map<String, Object> updatePayload = Map.of(
                "proposalId", "PRO-CRUD-001",
                "customerId", "CUST-001",
                "customerName", "Budi Santoso",
                "productCode", "LIFE-100",
                "age", 55,
                "occupation", "Driver"
        );

        given()
                .contentType("application/json")
                .body(updatePayload)
                .when()
                .put("/proposals/PRO-CRUD-001")
                .then()
                .statusCode(200)
                .body("customerName", equalTo("Budi Santoso"))
                .body("underwritingStatus", equalTo("REJECTED"));

        given()
                .when()
                .get("/proposals")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(1));

        given()
                .when()
                .delete("/proposals/PRO-CRUD-001")
                .then()
                .statusCode(204);

        given()
                .when()
                .get("/proposals/PRO-CRUD-001")
                .then()
                .statusCode(404);
    }
}
