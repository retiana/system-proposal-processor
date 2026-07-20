package com.ifglife.kafka.serialization;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PolicyProposalSubmittedEventDeserializerTest {

    @Test
    void constructorCreatesDeserializer() {
        PolicyProposalSubmittedEventDeserializer deserializer = new PolicyProposalSubmittedEventDeserializer();
        assertNotNull(deserializer);
    }
}
