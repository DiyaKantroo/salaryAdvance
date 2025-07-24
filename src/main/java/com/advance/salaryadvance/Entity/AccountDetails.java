package com.advance.salaryadvance.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class AccountDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private Double approvedLoanAmount;
    private Double emi;
    private Double interest;
    private Integer tenure;
    private String status;
    private String message;
} 