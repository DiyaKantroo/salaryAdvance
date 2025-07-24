package com.advance.salaryadvance.DTO;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequest {
    private String mobile;
    private String otp;

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getMobile() {
        return mobile;
    }

    public String getOtp() {
        return otp;
    }
}
