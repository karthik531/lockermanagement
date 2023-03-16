package com.karthik.service;

import com.karthik.utils.DefaultOTPGenerator;
import com.karthik.utils.OTPGenerator;

public class OTPService
{
    private OTPGenerator otpGenerator;

    public OTPService() {
        otpGenerator = new DefaultOTPGenerator(6);
    }

    public String generateOTP() {
        return otpGenerator.generateOTP();
    }

    public void sendOTP(String email, String otp) {
        System.out.println("OTP " + otp + " successfully sent to " + email);
    }
}
