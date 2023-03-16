package com.karthik.utils;

public class DefaultOTPGenerator implements OTPGenerator {

    private int otpCounter;
    private int lengthLimit;

    public DefaultOTPGenerator(int lengthLimit) {
        otpCounter = -1;
        this.lengthLimit = lengthLimit;
    }

    @Override
    public String generateOTP() {

        // increment the otpCounter
        otpCounter++;
        
        if(String.valueOf(otpCounter).length() > lengthLimit) {
            // reset otp to 0
            otpCounter = 0;
        }

        // take the otpCounter and pad it for 6 digits
        int otpLength = String.valueOf(otpCounter).length();
        StringBuilder otpCounterString = new StringBuilder("");

        while(otpLength < 6) {
            otpCounterString.append("0");
            otpLength++;
        }
        
        otpCounterString.append(String.valueOf(otpCounter));
        return otpCounterString.toString();
    }
}
