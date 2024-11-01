package com.banquemisr.moneytransactionservice.exception.custom;

public class OTPExpiredException extends RuntimeException{

    public OTPExpiredException() {}

    public OTPExpiredException(String msg) {
        super(msg);
    }
}
