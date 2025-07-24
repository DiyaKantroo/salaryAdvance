package com.advance.salaryadvance.Controller;

import com.advance.salaryadvance.DTO.OtpRequest;
import com.advance.salaryadvance.DTO.ApiResponse;
import com.advance.salaryadvance.Service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class OtpController {
    private final OtpService otpService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse> sendOtp(@RequestBody OtpRequest request) {
        String otp = otpService.generateOtp(request.getMobile());
        return ResponseEntity.ok(new ApiResponse(true, "OTP sent successfully (simulated). OTP: " + otp));
    }
}