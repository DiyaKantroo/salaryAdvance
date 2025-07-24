package com.advance.salaryadvance.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String mobile;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Boolean getOtpVerified() {
        return otpVerified;
    }

    public void setOtpVerified(Boolean otpVerified) {
        this.otpVerified = otpVerified;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getLoanReason() {
        return loanReason;
    }

    public void setLoanReason(String loanReason) {
        this.loanReason = loanReason;
    }

    public String getIncomeSource() {
        return incomeSource;
    }

    public void setIncomeSource(String incomeSource) {
        this.incomeSource = incomeSource;
    }

    public String getUploadedDocsPath() {
        return uploadedDocsPath;
    }

    public void setUploadedDocsPath(String uploadedDocsPath) {
        this.uploadedDocsPath = uploadedDocsPath;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    private Boolean otpVerified = false;

    private Double salary;

    private String loanReason;

    private String incomeSource;

    private String uploadedDocsPath;

    private LocalDateTime createdAt = LocalDateTime.now();

    private String aadharNumber;
    private String upiId;
}
