package com.ifglife.controller;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import com.ifglife.dto.api.ProposalRequest;
import com.ifglife.dto.api.ProposalResponse;
import com.ifglife.kafka.messeging.ProposalSubmissionProducer;
import com.ifglife.service.ProposalCrudService;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProposalControllerTest {

    @Test
    void submitPublishesEventAndReturnsAccepted() {
        ProposalSubmissionProducer producer = Mockito.mock(ProposalSubmissionProducer.class);
        ProposalController controller = new ProposalController();
        controller.proposalSubmissionProducer = producer;

        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        Response response = controller.submit(event);

        verify(producer).publish(event);
        assertEquals(202, response.getStatus());
    }

    @Test
    void createReturnsCreatedEntity() {
        ProposalCrudService crudService = Mockito.mock(ProposalCrudService.class);
        ProposalController controller = new ProposalController();
        controller.proposalCrudService = crudService;

        ProposalRequest request = new ProposalRequest();
        ProposalResponse created = new ProposalResponse();
        when(crudService.create(request)).thenReturn(created);

        Response response = controller.create(request);

        assertEquals(201, response.getStatus());
        assertSame(created, response.getEntity());
    }

    @Test
    void getAllDelegatesToService() {
        ProposalCrudService crudService = Mockito.mock(ProposalCrudService.class);
        ProposalController controller = new ProposalController();
        controller.proposalCrudService = crudService;

        List<ProposalResponse> expected = List.of(new ProposalResponse());
        when(crudService.getAll()).thenReturn(expected);

        assertSame(expected, controller.getAll());
    }

    @Test
    void getByIdDelegatesToService() {
        ProposalCrudService crudService = Mockito.mock(ProposalCrudService.class);
        ProposalController controller = new ProposalController();
        controller.proposalCrudService = crudService;

        ProposalResponse expected = new ProposalResponse();
        when(crudService.getById("P-1")).thenReturn(expected);

        assertSame(expected, controller.getById("P-1"));
    }

    @Test
    void updateDelegatesToService() {
        ProposalCrudService crudService = Mockito.mock(ProposalCrudService.class);
        ProposalController controller = new ProposalController();
        controller.proposalCrudService = crudService;

        ProposalRequest request = new ProposalRequest();
        ProposalResponse updated = new ProposalResponse();
        when(crudService.update("P-1", request)).thenReturn(updated);

        assertSame(updated, controller.update("P-1", request));
    }

    @Test
    void deleteReturnsNoContent() {
        ProposalCrudService crudService = Mockito.mock(ProposalCrudService.class);
        ProposalController controller = new ProposalController();
        controller.proposalCrudService = crudService;

        Response response = controller.delete("P-1");

        verify(crudService).delete("P-1");
        assertEquals(204, response.getStatus());
    }
}