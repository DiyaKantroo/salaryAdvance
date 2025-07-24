package com.advance.salaryadvance.DTO;

import lombok.Data;
 
@Data
public class LoanApplyRequest {
    private String aadharNumber;
    private String upiId;
    private Double requestedLoanAmount;
} 