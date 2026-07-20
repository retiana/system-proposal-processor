package com.ifglife.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "policy_proposals")
public class PolicyProposalEntity extends PanacheEntityBase {

    @Id
    @Column(name = "proposal_id", nullable = false, unique = true)
    private String proposalId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "customer_name", nullable = false)
    private String customerName;

    @Column(name = "product_code", nullable = false)
    private String productCode;

    @Column(name = "age", nullable = false)
    private int age;

    @Column(name = "occupation")
    private String occupation;

    @Column(name = "risk_score")
    private double riskScore;

    @Column(name = "underwriting_status")
    private String underwritingStatus;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public PolicyProposalEntity() {}

    public String getProposalId() { return proposalId; }
    public void setProposalId(String proposalId) { this.proposalId = proposalId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }

    public String getUnderwritingStatus() { return underwritingStatus; }
    public void setUnderwritingStatus(String underwritingStatus) { this.underwritingStatus = underwritingStatus; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}