package com.ifglife.kafka.messeging;

import com.ifglife.dto.PolicyProposalProcessedEvent;
import com.ifglife.dto.PolicyProposalSubmittedEvent;
import com.ifglife.entity.PolicyProposalEntity;
import com.ifglife.repository.PolicyProposalRepository;
import com.ifglife.service.RiskScoringService;
import com.ifglife.service.UnderwritingService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.time.LocalDateTime;

@ApplicationScoped
public class Consumer {

    @Inject
    RiskScoringService riskScoringService;

    @Inject
    UnderwritingService underwritingService;

    @Inject
    PolicyProposalRepository policyProposalRepository;

    @Inject
    Producer policyProducer;

    @Incoming("proposal-submitted-in")
    @Transactional
    public void consume(PolicyProposalSubmittedEvent event) {
        double riskScore = riskScoringService.calculateRiskScore(event);
        String underwritingStatus = underwritingService.determineStatus(riskScore);

        PolicyProposalEntity entity = new PolicyProposalEntity();
        entity.setProposalId(event.getProposalId());
        entity.setCustomerId(event.getCustomerId());
        entity.setCustomerName(event.getCustomerName());
        entity.setProductCode(event.getProductCode());
        entity.setAge(event.getAge());
        entity.setOccupation(event.getOccupation());
        entity.setRiskScore(riskScore);
        entity.setUnderwritingStatus(underwritingStatus);
        entity.setCreatedAt(LocalDateTime.now());
        policyProposalRepository.persist(entity);

        PolicyProposalProcessedEvent processedEvent = new PolicyProposalProcessedEvent();
        processedEvent.setProposalId(event.getProposalId());
        processedEvent.setRiskScore(riskScore);
        processedEvent.setUnderwritingStatus(underwritingStatus);
        policyProducer.publish(processedEvent);
    }
}