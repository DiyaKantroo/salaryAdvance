package com.advance.salaryadvance.DTO;

import lombok.Data;

@Data
public class LoanStatusResponse {
    private String status;
    private Double approvedLoanAmount;
    private Double emi;
    private Double interest;
    private Integer tenure;
    private String message;
} 