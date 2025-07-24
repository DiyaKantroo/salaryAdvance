package com.advance.salaryadvance.Service;

import com.advance.salaryadvance.DTO.OtpRequest;
import com.advance.salaryadvance.Entity.Customer;
import com.advance.salaryadvance.Repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final CustomerRepository customerRepository;
    private final OtpService otpService;

    public AuthService(CustomerRepository customerRepository, OtpService otpService) {
        this.customerRepository = customerRepository;
        this.otpService = otpService;
    }

    public Customer verifyOtp(OtpRequest request) {
        if (!otpService.validateOtp(request.getMobile(), request.getOtp())) {
            return null;
        }

        Customer customer = customerRepository.findByMobile(request.getMobile())
                .orElseGet(() -> {
                    Customer newCustomer = new Customer();
                    newCustomer.setMobile(request.getMobile());
                    return newCustomer;
                });

        customer.setOtpVerified(true);
        customerRepository.save(customer);

        return customer;
    }
}
