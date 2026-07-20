package com.ifglife.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolicyProposalProcessedEventTest {

    @Test
    void allArgsConstructorAssignsAllFields() {
        PolicyProposalProcessedEvent event = new PolicyProposalProcessedEvent("PRP-001", 30.0, "APPROVED");

        assertEquals("PRP-001", event.getProposalId());
        assertEquals(30.0, event.getRiskScore());
        assertEquals("APPROVED", event.getUnderwritingStatus());
    }

    @Test
    void settersAndGettersWork() {
        PolicyProposalProcessedEvent event = new PolicyProposalProcessedEvent();
        event.setProposalId("PRP-002");
        event.setRiskScore(70.0);
        event.setUnderwritingStatus("REJECTED");

        assertEquals("PRP-002", event.getProposalId());
        assertEquals(70.0, event.getRiskScore());
        assertEquals("REJECTED", event.getUnderwritingStatus());
    }
}
