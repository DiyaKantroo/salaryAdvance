package com.advance.salaryadvance.Controller;

import com.advance.salaryadvance.DTO.OtpRequest;
import com.advance.salaryadvance.Service.AuthService;
import com.advance.salaryadvance.DTO.ApiResponse;
import com.advance.salaryadvance.DTO.OtpVerifyResponse;
import com.advance.salaryadvance.Entity.Customer;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/verify")
    public ResponseEntity<OtpVerifyResponse> verifyOtp(@RequestBody OtpRequest request) {
        Customer customer = authService.verifyOtp(request);
        if (customer == null) {
            return ResponseEntity.badRequest().body(
                new OtpVerifyResponse(false, "Invalid or expired OTP", null)
            );
        }
        return ResponseEntity.ok(
            new OtpVerifyResponse(true, "OTP Verified Successfully", customer.getId())
        );
    }
}
