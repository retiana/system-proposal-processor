package com.ifglife.service;

import com.ifglife.dto.api.ProposalRequest;
import com.ifglife.dto.api.ProposalResponse;
import com.ifglife.entity.PolicyProposalEntity;
import com.ifglife.repository.PolicyProposalRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
public class ProposalCrudService {

    @Inject
    PolicyProposalRepository policyProposalRepository;

    @Inject
    RiskScoringService riskScoringService;

    @Inject
    UnderwritingService underwritingService;

    @Transactional
    public ProposalResponse create(ProposalRequest request) {
        validateRequest(request);

        if (policyProposalRepository.existsByProposalId(request.getProposalId())) {
            throw new WebApplicationException("Proposal already exists", Response.Status.CONFLICT);
        }

        PolicyProposalEntity entity = mapToNewEntity(request);
        policyProposalRepository.persist(entity);
        return toResponse(entity);
    }

    public List<ProposalResponse> getAll() {
        return policyProposalRepository.listAllByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public ProposalResponse getById(String proposalId) {
        return toResponse(requireById(proposalId));
    }

    @Transactional
    public ProposalResponse update(String proposalId, ProposalRequest request) {
        validateRequest(request);

        PolicyProposalEntity entity = requireById(proposalId);
        if (request.getProposalId() != null && !proposalId.equals(request.getProposalId())) {
            throw new WebApplicationException("Proposal ID in path and body must match", Response.Status.BAD_REQUEST);
        }

        applyEditableFields(entity, request);
        return toResponse(entity);
    }

    @Transactional
    public void delete(String proposalId) {
        PolicyProposalEntity entity = requireById(proposalId);
        policyProposalRepository.delete(entity);
    }

    private PolicyProposalEntity requireById(String proposalId) {
        return policyProposalRepository.findByProposalId(proposalId)
                .orElseThrow(() -> new NotFoundException("Proposal not found"));
    }

    private PolicyProposalEntity mapToNewEntity(ProposalRequest request) {
        PolicyProposalEntity entity = new PolicyProposalEntity();
        entity.setProposalId(request.getProposalId());
        entity.setCreatedAt(LocalDateTime.now());
        applyEditableFields(entity, request);
        return entity;
    }

    private void applyEditableFields(PolicyProposalEntity entity, ProposalRequest request) {
        entity.setCustomerId(request.getCustomerId());
        entity.setCustomerName(request.getCustomerName());
        entity.setProductCode(request.getProductCode());
        entity.setAge(request.getAge());
        entity.setOccupation(request.getOccupation());

        double riskScore = riskScoringService.calculateRiskScore(request.getAge(), request.getOccupation());
        entity.setRiskScore(riskScore);
        entity.setUnderwritingStatus(underwritingService.determineStatus(riskScore));
    }

    private ProposalResponse toResponse(PolicyProposalEntity entity) {
        ProposalResponse response = new ProposalResponse();
        response.setProposalId(entity.getProposalId());
        response.setCustomerId(entity.getCustomerId());
        response.setCustomerName(entity.getCustomerName());
        response.setProductCode(entity.getProductCode());
        response.setAge(entity.getAge());
        response.setOccupation(entity.getOccupation());
        response.setRiskScore(entity.getRiskScore());
        response.setUnderwritingStatus(entity.getUnderwritingStatus());
        response.setCreatedAt(entity.getCreatedAt());
        return response;
    }

    private void validateRequest(ProposalRequest request) {
        if (request == null) {
            throw new WebApplicationException("Request body is required", Response.Status.BAD_REQUEST);
        }
        if (isBlank(request.getProposalId())
                || isBlank(request.getCustomerId())
                || isBlank(request.getCustomerName())
                || isBlank(request.getProductCode())) {
            throw new WebApplicationException("proposalId, customerId, customerName, and productCode are required", Response.Status.BAD_REQUEST);
        }
        if (request.getAge() <= 0) {
            throw new WebApplicationException("Age must be greater than 0", Response.Status.BAD_REQUEST);
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}