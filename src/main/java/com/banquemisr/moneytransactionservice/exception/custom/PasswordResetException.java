package com.banquemisr.moneytransactionservice.exception.custom;

public class PasswordResetException extends RuntimeException{
    public PasswordResetException () {}

    public PasswordResetException(String msg) {
        super(msg);
    }
}
