package com.ifglife.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class PolicyProposalRepositoryTest {

    @Test
    void repositoryCanBeInstantiated() {
        PolicyProposalRepository repository = new PolicyProposalRepository();
        assertNotNull(repository);
    }
}
