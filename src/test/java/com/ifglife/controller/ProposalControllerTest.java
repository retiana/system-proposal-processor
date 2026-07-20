package com.ifglife.controller;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import com.ifglife.kafka.messeging.ProposalSubmissionProducer;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

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
}
