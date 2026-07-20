package com.ifglife.dto;

public class PolicyProposalProcessedEvent {

    private String proposalId;
    private double riskScore;
    private String underwritingStatus;

    public PolicyProposalProcessedEvent() {}

    public PolicyProposalProcessedEvent(String proposalId, double riskScore, String underwritingStatus) {
        this.proposalId = proposalId;
        this.riskScore = riskScore;
        this.underwritingStatus = underwritingStatus;
    }

    public String getProposalId() { return proposalId; }
    public void setProposalId(String proposalId) { this.proposalId = proposalId; }

    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }

    public String getUnderwritingStatus() { return underwritingStatus; }
    public void setUnderwritingStatus(String underwritingStatus) { this.underwritingStatus = underwritingStatus; }
}