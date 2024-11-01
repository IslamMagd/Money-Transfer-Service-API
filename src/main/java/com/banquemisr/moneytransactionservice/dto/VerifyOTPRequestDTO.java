package com.banquemisr.moneytransactionservice.dto;


import lombok.Data;

@Data
public class VerifyOTPRequestDTO {
    private String otp;
    private String accountNumber;
}
