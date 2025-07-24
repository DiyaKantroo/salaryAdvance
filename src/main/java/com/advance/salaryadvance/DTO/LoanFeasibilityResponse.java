package com.advance.salaryadvance.DTO;

import lombok.Data;

@Data
public class LoanFeasibilityResponse {
    private boolean eligible;
    private Double approvedLoanAmount;
    private Double interestRate;
    private Double emi;
    private Integer tenure;
    private String message;
} 