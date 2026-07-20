package com.ifglife.dto;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolicyProposalSubmittedEventTest {

    @Test
    void allArgsConstructorAssignsAllFields() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent(
                "PRP-001",
                "CUS-001",
                "Budi Santoso",
                "LIFE001",
                new BigDecimal("500000000"),
                35,
                "Software Engineer",
                new BigDecimal("250000000")
        );

        assertEquals("PRP-001", event.getProposalId());
        assertEquals("CUS-001", event.getCustomerId());
        assertEquals("Budi Santoso", event.getCustomerName());
        assertEquals("LIFE001", event.getProductCode());
        assertEquals(new BigDecimal("500000000"), event.getSumAssured());
        assertEquals(35, event.getAge());
        assertEquals("Software Engineer", event.getOccupation());
        assertEquals(new BigDecimal("250000000"), event.getAnnualIncome());
    }

    @Test
    void settersAndGettersWork() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setProposalId("PRP-002");
        event.setCustomerId("CUS-002");
        event.setCustomerName("Siti");
        event.setProductCode("LIFE002");
        event.setSumAssured(new BigDecimal("100000000"));
        event.setAge(45);
        event.setOccupation("Teacher");
        event.setAnnualIncome(new BigDecimal("120000000"));

        assertEquals("PRP-002", event.getProposalId());
        assertEquals("CUS-002", event.getCustomerId());
        assertEquals("Siti", event.getCustomerName());
        assertEquals("LIFE002", event.getProductCode());
        assertEquals(new BigDecimal("100000000"), event.getSumAssured());
        assertEquals(45, event.getAge());
        assertEquals("Teacher", event.getOccupation());
        assertEquals(new BigDecimal("120000000"), event.getAnnualIncome());
    }
}
