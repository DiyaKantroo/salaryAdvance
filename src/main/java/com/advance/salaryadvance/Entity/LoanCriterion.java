package com.advance.salaryadvance.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LoanCriterion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double minSalary;
    private Double maxLoanAmount;
    private Double interestRate;
    private Integer maxTenure;
} 