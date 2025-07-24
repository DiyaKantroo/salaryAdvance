package com.advance.salaryadvance.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDetailsRequest {
    private UUID customerId;
    private Double salary;
    private String loanReason;
    private String incomeSource;
    private String uploadedDocsPath; // For simplicity, path or base64 string
} 