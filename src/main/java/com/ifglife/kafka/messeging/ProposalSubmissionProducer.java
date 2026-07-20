package com.ifglife.kafka.messeging;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class ProposalSubmissionProducer {

    @Channel("proposal-submitted-out")
    Emitter<PolicyProposalSubmittedEvent> emitter;

    public void publish(PolicyProposalSubmittedEvent event) {
        emitter.send(event);
    }
}
