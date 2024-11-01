package com.banquemisr.moneytransactionservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull(message = "this field Can not be null")
    @NotBlank(message = "this field Can not be Blank")
    private String value;
    private LocalTime timestamp;

    @OneToOne(mappedBy = "otp")
    private Account account;
}
