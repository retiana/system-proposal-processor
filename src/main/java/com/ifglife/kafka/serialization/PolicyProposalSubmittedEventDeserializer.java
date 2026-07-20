package com.ifglife.kafka.serialization;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;

public class PolicyProposalSubmittedEventDeserializer extends ObjectMapperDeserializer<PolicyProposalSubmittedEvent> {

    public PolicyProposalSubmittedEventDeserializer() {
        super(PolicyProposalSubmittedEvent.class);
    }
}
