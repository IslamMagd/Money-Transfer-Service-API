package com.banquemisr.moneytransactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionDTO {
    private String fromAccountNumber;
    private String toAccountNumber;
    private Double amount;
    private LocalDate transactionData;
    private LocalTime transactionTime;
}
