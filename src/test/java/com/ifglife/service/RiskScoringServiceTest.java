package com.ifglife.service;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskScoringServiceTest {

    private final RiskScoringService service = new RiskScoringService();

    @Test
    void calculatesScoreForAge31To45AndSoftwareEngineer() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setAge(35);
        event.setOccupation("Software Engineer");

        assertEquals(30.0, service.calculateRiskScore(event));
    }

    @Test
    void calculatesScoreForAge46To60AndDriver() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setAge(50);
        event.setOccupation("Driver");

        assertEquals(70.0, service.calculateRiskScore(event));
    }

    @Test
    void calculatesScoreForAgeAbove60AndConstructionWorker() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setAge(61);
        event.setOccupation("Construction Worker");

        assertEquals(110.0, service.calculateRiskScore(event));
    }

    @Test
    void returnsOnlyAgeScoreWhenOccupationUnknown() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setAge(40);
        event.setOccupation("Pilot");

        assertEquals(20.0, service.calculateRiskScore(event));
    }

    @Test
    void returnsOnlyOccupationScoreWhenAgeOutsideRules() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setAge(30);
        event.setOccupation("Teacher");

        assertEquals(10.0, service.calculateRiskScore(event));
    }

    @Test
    void handlesNullOccupation() {
        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setAge(45);

        assertEquals(20.0, service.calculateRiskScore(event));
    }
}
