package com.ifglife.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnderwritingServiceTest {

    private final UnderwritingService service = new UnderwritingService();

    @Test
    void returnsApprovedWhenRiskAtOrBelow30() {
        assertEquals("APPROVED", service.determineStatus(30));
        assertEquals("APPROVED", service.determineStatus(0));
    }

    @Test
    void returnsReviewWhenRiskBetween31And60() {
        assertEquals("REVIEW", service.determineStatus(31));
        assertEquals("REVIEW", service.determineStatus(60));
    }

    @Test
    void returnsRejectedWhenRiskAbove60() {
        assertEquals("REJECTED", service.determineStatus(60.1));
        assertEquals("REJECTED", service.determineStatus(100));
    }
}
