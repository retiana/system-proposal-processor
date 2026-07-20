package com.ifglife.dto;

import java.math.BigDecimal;

public class PolicyProposalSubmittedEvent {

    private String proposalId;
    private String customerId;
    private String customerName;
    private String productCode;
    private BigDecimal sumAssured;
    private int age;
    private String occupation;
    private BigDecimal annualIncome;

    public PolicyProposalSubmittedEvent() {}

    public PolicyProposalSubmittedEvent(String proposalId, String customerId, String customerName,
                                        String productCode, BigDecimal sumAssured, int age,
                                        String occupation, BigDecimal annualIncome) {
        this.proposalId = proposalId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.productCode = productCode;
        this.sumAssured = sumAssured;
        this.age = age;
        this.occupation = occupation;
        this.annualIncome = annualIncome;
    }

    public String getProposalId() { return proposalId; }
    public void setProposalId(String proposalId) { this.proposalId = proposalId; }

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }

    public BigDecimal getSumAssured() { return sumAssured; }
    public void setSumAssured(BigDecimal sumAssured) { this.sumAssured = sumAssured; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getOccupation() { return occupation; }
    public void setOccupation(String occupation) { this.occupation = occupation; }

    public BigDecimal getAnnualIncome() { return annualIncome; }
    public void setAnnualIncome(BigDecimal annualIncome) { this.annualIncome = annualIncome; }
}