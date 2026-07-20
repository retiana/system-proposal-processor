package com.ifglife.kafka.messeging;

import com.ifglife.dto.PolicyProposalProcessedEvent;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

class PolicyProducerTest {

    @Test
    void publishSendsProcessedEventToEmitter() {
        @SuppressWarnings("unchecked")
        Emitter<PolicyProposalProcessedEvent> emitter = Mockito.mock(Emitter.class);

        Producer producer = new Producer();
        producer.emitter = emitter;

        PolicyProposalProcessedEvent event = new PolicyProposalProcessedEvent();
        producer.publish(event);

        verify(emitter).send(event);
    }
}
