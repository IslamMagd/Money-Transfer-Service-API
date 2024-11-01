package com.banquemisr.moneytransactionservice.exception.custom;

public class OTPNotFoundException extends RuntimeException{
    public OTPNotFoundException () {}

    public OTPNotFoundException(String msg) {
        super(msg);
    }
}
