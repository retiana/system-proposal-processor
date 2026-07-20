package com.ifglife.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolicyProposalEntityTest {

    @Test
    void settersAndGettersWork() {
        PolicyProposalEntity entity = new PolicyProposalEntity();
        LocalDateTime now = LocalDateTime.now();

        entity.setProposalId("PRP-001");
        entity.setCustomerId("CUS-001");
        entity.setCustomerName("Budi Santoso");
        entity.setProductCode("LIFE001");
        entity.setAge(35);
        entity.setOccupation("Software Engineer");
        entity.setRiskScore(30.0);
        entity.setUnderwritingStatus("APPROVED");
        entity.setCreatedAt(now);

        assertEquals("PRP-001", entity.getProposalId());
        assertEquals("CUS-001", entity.getCustomerId());
        assertEquals("Budi Santoso", entity.getCustomerName());
        assertEquals("LIFE001", entity.getProductCode());
        assertEquals(35, entity.getAge());
        assertEquals("Software Engineer", entity.getOccupation());
        assertEquals(30.0, entity.getRiskScore());
        assertEquals("APPROVED", entity.getUnderwritingStatus());
        assertEquals(now, entity.getCreatedAt());
    }
}
