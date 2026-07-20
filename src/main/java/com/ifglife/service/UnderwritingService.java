package com.ifglife.service;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UnderwritingService {

    public String determineStatus(double riskScore) {
        if (riskScore <= 30) {
            return "APPROVED";
        } else if (riskScore <= 60) {
            return "REVIEW";
        } else {
            return "REJECTED";
        }
    }
}