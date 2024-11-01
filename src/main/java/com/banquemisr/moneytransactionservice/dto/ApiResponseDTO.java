package com.banquemisr.moneytransactionservice.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {
    private String message;
    private HttpStatus status;
    private LocalDateTime timestamp;
}
