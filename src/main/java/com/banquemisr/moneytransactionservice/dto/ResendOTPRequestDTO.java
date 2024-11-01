package com.banquemisr.moneytransactionservice.dto;

import lombok.Data;

@Data
public class ResendOTPRequestDTO {
    private String email;
    private String accountNumber;
}

