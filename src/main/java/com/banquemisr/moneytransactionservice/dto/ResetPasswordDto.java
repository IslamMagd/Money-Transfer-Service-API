package com.banquemisr.moneytransactionservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordDto {

    private String currentPassword;
    private String newPassword;
}
