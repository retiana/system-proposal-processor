package com.ifglife.kafka.messeging;

import com.ifglife.dto.PolicyProposalProcessedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class Producer {

    @Channel("proposal-processed-out")
    Emitter<PolicyProposalProcessedEvent> emitter;

    public void publish(PolicyProposalProcessedEvent event) {
        emitter.send(event);
    }
}