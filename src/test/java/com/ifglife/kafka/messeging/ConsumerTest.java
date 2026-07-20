package com.ifglife.kafka.messeging;

import com.ifglife.dto.PolicyProposalProcessedEvent;
import com.ifglife.dto.PolicyProposalSubmittedEvent;
import com.ifglife.entity.PolicyProposalEntity;
import com.ifglife.repository.PolicyProposalRepository;
import com.ifglife.service.RiskScoringService;
import com.ifglife.service.UnderwritingService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConsumerTest {

    @Test
    void consumeScoresPersistsAndPublishesProcessedEvent() {
        RiskScoringService riskScoringService = Mockito.mock(RiskScoringService.class);
        UnderwritingService underwritingService = Mockito.mock(UnderwritingService.class);
        PolicyProposalRepository policyProposalRepository = Mockito.mock(PolicyProposalRepository.class);
        Producer policyProducer = Mockito.mock(Producer.class);

        Consumer consumer = new Consumer();
        consumer.riskScoringService = riskScoringService;
        consumer.underwritingService = underwritingService;
        consumer.policyProposalRepository = policyProposalRepository;
        consumer.policyProducer = policyProducer;

        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        event.setProposalId("PRP-001");
        event.setCustomerId("CUS-001");
        event.setCustomerName("Budi Santoso");
        event.setProductCode("LIFE001");
        event.setAge(35);
        event.setOccupation("Software Engineer");

        when(riskScoringService.calculateRiskScore(event)).thenReturn(30.0);
        when(underwritingService.determineStatus(30.0)).thenReturn("APPROVED");

        consumer.consume(event);

        verify(riskScoringService).calculateRiskScore(event);
        verify(underwritingService).determineStatus(30.0);

        ArgumentCaptor<PolicyProposalEntity> entityCaptor = ArgumentCaptor.forClass(PolicyProposalEntity.class);
        verify(policyProposalRepository).persist(entityCaptor.capture());
        PolicyProposalEntity persisted = entityCaptor.getValue();
        assertEquals("PRP-001", persisted.getProposalId());
        assertEquals("CUS-001", persisted.getCustomerId());
        assertEquals("Budi Santoso", persisted.getCustomerName());
        assertEquals("LIFE001", persisted.getProductCode());
        assertEquals(35, persisted.getAge());
        assertEquals("Software Engineer", persisted.getOccupation());
        assertEquals(30.0, persisted.getRiskScore());
        assertEquals("APPROVED", persisted.getUnderwritingStatus());
        assertNotNull(persisted.getCreatedAt());

        ArgumentCaptor<PolicyProposalProcessedEvent> processedCaptor = ArgumentCaptor.forClass(PolicyProposalProcessedEvent.class);
        verify(policyProducer).publish(processedCaptor.capture());
        PolicyProposalProcessedEvent processed = processedCaptor.getValue();
        assertEquals("PRP-001", processed.getProposalId());
        assertEquals(30.0, processed.getRiskScore());
        assertEquals("APPROVED", processed.getUnderwritingStatus());
    }
}
