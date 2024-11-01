package com.banquemisr.moneytransactionservice.controller;



import com.banquemisr.moneytransactionservice.dto.ApiResponseDTO;
import com.banquemisr.moneytransactionservice.dto.ResendOTPRequestDTO;
import com.banquemisr.moneytransactionservice.dto.VerifyOTPRequestDTO;
import com.banquemisr.moneytransactionservice.exception.ErrorResponse;
import com.banquemisr.moneytransactionservice.service.IOTP;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "OTP Controller", description = "The OTP controller endpoints")
public class OTPController {

    private final IOTP otpService;


    @Operation(summary = "Verify and activate account")
    @ApiResponse(responseCode = "202", description = "OTP is verified, Account activation is successful", content = {@Content(schema = @Schema(implementation = ApiResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "Account or OTP don't exist", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "400", description = "OTP expired", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponseDTO> verifyOTP(@RequestBody VerifyOTPRequestDTO verifyOTPRequest) {
        log.info("{}", verifyOTPRequest);
        otpService.verifyOTP(verifyOTPRequest.getOtp(), verifyOTPRequest.getAccountNumber());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponseDTO("OTP is verified, Account is activated",
                        HttpStatus.ACCEPTED,
                        LocalDateTime.now()
                )
        );
    }

    @Operation(summary = "Resend otp mail to user")
    @ApiResponse(responseCode = "200", description = "OTP is sent successfully", content = {@Content(schema = @Schema(implementation = ApiResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "500", description = "Error Sending Message", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponseDTO> resendOTp(@RequestBody ResendOTPRequestDTO resendOTPRequestDTO) throws MessagingException {
        otpService.resendOTP(resendOTPRequestDTO.getEmail(), resendOTPRequestDTO.getAccountNumber());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                new ApiResponseDTO("OTP is resend successfully",
                        HttpStatus.OK,
                        LocalDateTime.now()
                )
        );
    }


}
