package com.ifglife.service;

import com.ifglife.dto.PolicyProposalSubmittedEvent;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RiskScoringService {

    public double calculateRiskScore(PolicyProposalSubmittedEvent event) {
        return calculateRiskScore(event.getAge(), event.getOccupation());
    }

    public double calculateRiskScore(int age, String occupation) {
        double score = 0;

        // Age scoring
        if (age >= 31 && age <= 45) {
            score += 20;
        } else if (age >= 46 && age <= 60) {
            score += 40;
        } else if (age > 60) {
            score += 60;
        }

        // Occupation scoring
        if (occupation != null) {
            switch (occupation.trim()) {
                case "Software Engineer" -> score += 10;
                case "Teacher" -> score += 10;
                case "Office Worker" -> score += 15;
                case "Driver" -> score += 30;
                case "Construction Worker" -> score += 50;
            }
        }

        return score;
    }
}