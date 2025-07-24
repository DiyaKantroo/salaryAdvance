package com.advance.salaryadvance.Service;

import com.advance.salaryadvance.DTO.*;
import com.advance.salaryadvance.Entity.LoanCriterion;
import com.advance.salaryadvance.Entity.Customer;
import com.advance.salaryadvance.Entity.AccountDetails;
import com.advance.salaryadvance.Repository.CustomerRepository;
import com.advance.salaryadvance.Repository.LoanCriterionRepository;
import com.advance.salaryadvance.Repository.AccountDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanCriterionRepository loanCriterionRepository;
    @Autowired
    private AccountDetailsRepository accountDetailsRepository;

    public Object saveCustomerDetails(CustomerDetailsRequest request) {
        if (request.getCustomerId() == null) {
            return new ApiResponse(false, "Customer ID is required");
        }
        return customerRepository.findById(request.getCustomerId())
            .map(customer -> {
                customer.setSalary(request.getSalary());
                customer.setLoanReason(request.getLoanReason());
                customer.setIncomeSource(request.getIncomeSource());
                customer.setUploadedDocsPath(request.getUploadedDocsPath());
                customerRepository.save(customer);
                return new ApiResponse(true, "Customer details saved successfully");
            })
            .orElseGet(() -> new ApiResponse(false, "Customer not found"));
    }
    public LoanFeasibilityResponse checkLoanFeasibility(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        LoanFeasibilityResponse resp = new LoanFeasibilityResponse();
        if (customer == null || customer.getSalary() == null) {
            resp.setEligible(false);
            resp.setMessage("Customer not found or salary not set");
            return resp;
        }
        LoanCriterion criterion = loanCriterionRepository.findAll().stream()
                .filter(c -> customer.getSalary() >= c.getMinSalary())
                .findFirst().orElse(null);
        if (criterion == null) {
            resp.setEligible(false);
            resp.setMessage("Not eligible for loan");
            return resp;
        }
        double maxAllowed = customer.getSalary() * 0.7;
        double principal = Math.min(maxAllowed, criterion.getMaxLoanAmount());
        if (principal < 1) {
            resp.setEligible(false);
            resp.setMessage("Requested loan exceeds 70% of salary");
            return resp;
        }
        double rate = criterion.getInterestRate() / 12 / 100;
        int tenure = criterion.getMaxTenure();
        double emi = (principal * rate * Math.pow(1 + rate, tenure)) / (Math.pow(1 + rate, tenure) - 1);
        resp.setEligible(true);
        resp.setApprovedLoanAmount(principal);
        resp.setInterestRate(criterion.getInterestRate());
        resp.setEmi(Math.round(emi * 100.0) / 100.0);
        resp.setTenure(tenure);
        resp.setMessage("Eligible for loan");
        return resp;
    }
    public ApiResponse applyLoan(UUID customerId, LoanApplyRequest request) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            // Save a rejected status
            saveLoanStatus(customer, null, "Rejected", "Customer not found");
            return new ApiResponse(false, "Customer not found");
        }
        // Check eligibility
        LoanFeasibilityResponse feasibility = checkLoanFeasibility(customerId);
        if (!feasibility.isEligible()) {
            saveLoanStatus(customer, null, "Rejected", "Customer not eligible for loan");
            return new ApiResponse(false, "Customer not eligible for loan");
        }
        if (request.getRequestedLoanAmount() == null) {
            saveLoanStatus(customer, null, "Rejected", "Requested loan amount is required");
            return new ApiResponse(false, "Requested loan amount is required");
        }
        double maxAllowed = customer.getSalary() * 0.7;
        if (request.getRequestedLoanAmount() > maxAllowed) {
            saveLoanStatus(customer, null, "Rejected", "Requested loan exceeds 70% of salary. Loan rejected.");
            return new ApiResponse(false, "Requested loan exceeds 70% of salary. Loan rejected.");
        }
        if (request.getRequestedLoanAmount() > feasibility.getApprovedLoanAmount()) {
            saveLoanStatus(customer, null, "Rejected", "Requested loan exceeds approved amount");
            return new ApiResponse(false, "Requested loan exceeds approved amount");
        }
        // Save Aadhaar and UPI
        customer.setAadharNumber(request.getAadharNumber());
        customer.setUpiId(request.getUpiId());
        customerRepository.save(customer);
        // Create AccountDetails for approved loan
        AccountDetails details = new AccountDetails();
        details.setCustomer(customer);
        details.setApprovedLoanAmount(request.getRequestedLoanAmount());
        details.setEmi(feasibility.getEmi());
        details.setInterest(feasibility.getInterestRate());
        details.setTenure(feasibility.getTenure());
        details.setStatus("Active");
        details.setMessage("Loan is active");
        accountDetailsRepository.save(details);
        return new ApiResponse(true, "Loan disbursed successfully");
    }

    private void saveLoanStatus(Customer customer, Double amount, String status, String message) {
        if (customer == null) return;
        AccountDetails details = new AccountDetails();
        details.setCustomer(customer);
        details.setApprovedLoanAmount(amount);
        details.setStatus(status);
        details.setMessage(message);
        accountDetailsRepository.save(details);
    }

    public LoanStatusResponse getLoanStatus(UUID customerId) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        LoanStatusResponse resp = new LoanStatusResponse();
        if (customer == null) {
            resp.setStatus("No Loan");
            resp.setMessage("Customer not found");
            return resp;
        }
        // Find the latest loan status for this customer
        AccountDetails details = accountDetailsRepository.findAll().stream()
                .filter(a -> a.getCustomer().getId().equals(customerId))
                .reduce((first, second) -> second) // get the last (latest) if multiple
                .orElse(null);
        if (details == null) {
            resp.setStatus("No Loan");
            resp.setMessage("No loan found for this customer");
            return resp;
        }
        resp.setStatus(details.getStatus() != null ? details.getStatus() : "Active");
        resp.setApprovedLoanAmount(details.getApprovedLoanAmount());
        resp.setEmi(details.getEmi());
        resp.setInterest(details.getInterest());
        resp.setTenure(details.getTenure());
        resp.setMessage(details.getMessage() != null ? details.getMessage() : "Loan is active");
        return resp;
    }
} 

// b3cf0ca8-cae7-4200-a821-0f593fc1abec