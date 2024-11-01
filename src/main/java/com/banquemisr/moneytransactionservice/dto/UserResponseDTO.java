package com.banquemisr.moneytransactionservice.dto;


import com.banquemisr.moneytransactionservice.dto.enums.Country;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String fullname;
    private String email;
    private LocalDate dateOfBirth;
    private Country country;
    private String accountNumber;
}
