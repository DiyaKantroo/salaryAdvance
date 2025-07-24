package com.advance.salaryadvance.Service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {
    private final Map<String, OtpEntry> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateOtp(String mobile) {
        String otp = String.format("%06d", random.nextInt(999999));
        otpStore.put(mobile, new OtpEntry(otp, LocalDateTime.now().plusMinutes(5)));
        // Simulate sending OTP (integrate with SMS provider here)
        System.out.println("Sending OTP " + otp + " to mobile " + mobile);
        return otp;
    }

    public boolean validateOtp(String mobile, String otp) {
        OtpEntry entry = otpStore.get(mobile);
        if (entry == null) return false;
        if (entry.expiry.isBefore(LocalDateTime.now())) {
            otpStore.remove(mobile);
            return false;
        }
        boolean valid = entry.otp.equals(otp);
        if (valid) otpStore.remove(mobile); // OTP can be used only once
        return valid;
    }

    private static class OtpEntry {
        String otp;
        LocalDateTime expiry;
        OtpEntry(String otp, LocalDateTime expiry) {
            this.otp = otp;
            this.expiry = expiry;
        }
    }
} 