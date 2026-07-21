package com.ifglife.service;

import com.ifglife.dto.api.ProposalRequest;
import com.ifglife.dto.api.ProposalResponse;
import com.ifglife.entity.PolicyProposalEntity;
import com.ifglife.repository.PolicyProposalRepository;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.WebApplicationException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProposalCrudServiceTest {

    @Test
    void createPersistsEntityWithCalculatedRiskAndStatus() {
        PolicyProposalRepository repository = Mockito.mock(PolicyProposalRepository.class);
        RiskScoringService riskScoringService = Mockito.mock(RiskScoringService.class);
        UnderwritingService underwritingService = Mockito.mock(UnderwritingService.class);

        ProposalCrudService service = new ProposalCrudService();
        service.policyProposalRepository = repository;
        service.riskScoringService = riskScoringService;
        service.underwritingService = underwritingService;

        ProposalRequest request = validRequest("P-1");
        when(repository.existsByProposalId("P-1")).thenReturn(false);
        when(riskScoringService.calculateRiskScore(35, "Teacher")).thenReturn(42.0);
        when(underwritingService.determineStatus(42.0)).thenReturn("REVIEW");

        ProposalResponse created = service.create(request);

        ArgumentCaptor<PolicyProposalEntity> captor = ArgumentCaptor.forClass(PolicyProposalEntity.class);
        verify(repository).persist(captor.capture());

        PolicyProposalEntity persisted = captor.getValue();
        assertEquals("P-1", persisted.getProposalId());
        assertEquals("C-1", persisted.getCustomerId());
        assertEquals(42.0, persisted.getRiskScore());
        assertEquals("REVIEW", persisted.getUnderwritingStatus());
        assertTrue(persisted.getCreatedAt() != null);

        assertEquals("P-1", created.getProposalId());
        assertEquals("REVIEW", created.getUnderwritingStatus());
    }

    @Test
    void createThrowsConflictWhenProposalAlreadyExists() {
        PolicyProposalRepository repository = Mockito.mock(PolicyProposalRepository.class);

        ProposalCrudService service = new ProposalCrudService();
        service.policyProposalRepository = repository;
        service.riskScoringService = Mockito.mock(RiskScoringService.class);
        service.underwritingService = Mockito.mock(UnderwritingService.class);

        when(repository.existsByProposalId("P-1")).thenReturn(true);

        WebApplicationException ex = assertThrows(WebApplicationException.class, () -> service.create(validRequest("P-1")));
        assertEquals(409, ex.getResponse().getStatus());
    }

    @Test
    void getByIdThrowsNotFoundWhenMissing() {
        PolicyProposalRepository repository = Mockito.mock(PolicyProposalRepository.class);

        ProposalCrudService service = new ProposalCrudService();
        service.policyProposalRepository = repository;

        when(repository.findByProposalId("P-404")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.getById("P-404"));
    }

    @Test
    void getAllReturnsMappedListFromRepository() {
        PolicyProposalRepository repository = Mockito.mock(PolicyProposalRepository.class);

        ProposalCrudService service = new ProposalCrudService();
        service.policyProposalRepository = repository;

        PolicyProposalEntity entity = new PolicyProposalEntity();
        entity.setProposalId("P-2");
        entity.setCustomerId("C-2");
        entity.setCustomerName("Sari");
        entity.setProductCode("PRD-2");
        entity.setAge(28);
        entity.setOccupation("Teacher");
        entity.setRiskScore(10);
        entity.setUnderwritingStatus("APPROVED");
        entity.setCreatedAt(LocalDateTime.now());

        when(repository.listAllByCreatedAtDesc()).thenReturn(List.of(entity));

        List<ProposalResponse> result = service.getAll();
        assertEquals(1, result.size());
        assertEquals("P-2", result.get(0).getProposalId());
    }

    @Test
    void deleteUsesRepositoryDelete() {
        PolicyProposalRepository repository = Mockito.mock(PolicyProposalRepository.class);

        ProposalCrudService service = new ProposalCrudService();
        service.policyProposalRepository = repository;

        PolicyProposalEntity entity = new PolicyProposalEntity();
        when(repository.findByProposalId("P-1")).thenReturn(Optional.of(entity));

        service.delete("P-1");

        verify(repository).delete(entity);
    }

    private ProposalRequest validRequest(String proposalId) {
        ProposalRequest request = new ProposalRequest();
        request.setProposalId(proposalId);
        request.setCustomerId("C-1");
        request.setCustomerName("Budi");
        request.setProductCode("PRD-1");
        request.setAge(35);
        request.setOccupation("Teacher");
        return request;
    }
}