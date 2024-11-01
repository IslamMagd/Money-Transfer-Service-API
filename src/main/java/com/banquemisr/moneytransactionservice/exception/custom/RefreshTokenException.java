package com.banquemisr.moneytransactionservice.exception.custom;

public class RefreshTokenException extends RuntimeException{

    public RefreshTokenException() {}

    public RefreshTokenException(String msg) {
        super(msg);
    }
}
