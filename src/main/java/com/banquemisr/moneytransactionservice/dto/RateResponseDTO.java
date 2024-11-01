package com.banquemisr.moneytransactionservice.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RateResponseDTO {
    private LocalDateTime date;
    private String from;
    private String to;
    private double rate;
    private double givenAmount;
    private double convertedAmount;
}
