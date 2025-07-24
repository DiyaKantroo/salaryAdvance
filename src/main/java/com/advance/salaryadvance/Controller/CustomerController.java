package com.advance.salaryadvance.Controller;

import com.advance.salaryadvance.DTO.*;
import com.advance.salaryadvance.Service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/details")
    public ResponseEntity<?> submitDetails(@RequestBody CustomerDetailsRequest request) {
        return ResponseEntity.ok(customerService.saveCustomerDetails(request));
    }

    @GetMapping("/loan/feasibility/{customerId}")
    public ResponseEntity<?> checkFeasibility(@PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.checkLoanFeasibility(customerId));
    }

    @PostMapping("/loan/apply/{customerId}")
    public ResponseEntity<?> applyLoan(@PathVariable UUID customerId, @RequestBody LoanApplyRequest request) {
        return ResponseEntity.ok(customerService.applyLoan(customerId, request));
    }

    @GetMapping("/loan/status/{customerId}")
    public ResponseEntity<?> loanStatus(@PathVariable UUID customerId) {
        return ResponseEntity.ok(customerService.getLoanStatus(customerId));
    }
} 