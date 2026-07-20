package com.ifglife.kafka.messeging;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class ProposalSubmissionProducerTest {

    @Test
    void publishSendsEventToEmitter() {
        @SuppressWarnings("unchecked")
        Emitter<PolicyProposalSubmittedEvent> emitter = Mockito.mock(Emitter.class);

        ProposalSubmissionProducer producer = new ProposalSubmissionProducer();
        producer.emitter = emitter;

        PolicyProposalSubmittedEvent event = new PolicyProposalSubmittedEvent();
        producer.publish(event);

        verify(emitter).send(event);
    }
}
