package com.banquemisr.moneytransactionservice.controller;

import com.banquemisr.moneytransactionservice.dto.*;
import com.banquemisr.moneytransactionservice.exception.ErrorResponse;
import com.banquemisr.moneytransactionservice.service.IUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final IUser userService;


    @Operation(summary = "Get user based on the auth token")
    @ApiResponse(responseCode = "200", description = "User retrieved successfully", content = {@Content(schema = @Schema(implementation = UserResponseDTO.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "User not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    @ApiResponse(responseCode = "404", description = "User bank account not found", content = {@Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")})
    @GetMapping("/user")
    public ResponseEntity<UserResponseDTO> getUserByToken(
            @RequestHeader("Authorization") String token
    ) {
        var user = this.userService.getUser(token);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Changes rate to EGP")
    @ApiResponse(responseCode = "200", description = "Rate changed successfully", content = {@Content(schema = @Schema(implementation = Double.class), mediaType = "application/json")})
    @PostMapping("/ratetoEGP")
    public ResponseEntity<Map<String, Double>> getRateToEGP(@RequestBody CurrencyRateDTO currencyRateDTO) {
        Map<String, Double> rate = new HashMap<>();
        rate.put("rate", this.userService.getRateToEGP(currencyRateDTO));
        return new ResponseEntity<>(
                rate,
                HttpStatus.OK
        );
    }

    @Operation(summary = "Changes rate from EGP")
    @ApiResponse(responseCode = "200", description = "Rate changed successfully", content = {@Content(schema = @Schema(implementation = Double.class), mediaType = "application/json")})
    @PostMapping("/ratefromEGP")
    public ResponseEntity<Map<String, Double>> getRateFromEGP(@RequestBody CurrencyRateDTO currencyRateDTO) {
        Map<String, Double> rate = new HashMap<>();
        rate.put("rate", this.userService.getRateFromEGP(currencyRateDTO));
        return new ResponseEntity<>(
                rate,
                HttpStatus.OK
        );
    }

    @Operation(summary = "Changes rate to inputted currencies")
    @ApiResponse(responseCode = "200", description = "Rate changed successfully", content = {@Content(schema = @Schema(implementation = Double.class), mediaType = "application/json")})
    @PostMapping("/rate")
    public ResponseEntity<Map<String, Double>> getRate(@RequestBody CurrencyToFromRateDTO currencyToFromRateDTO) {
        Map<String, Double> rate = new HashMap<>();
        rate.put("rate", this.userService.getRate(currencyToFromRateDTO));
        return new ResponseEntity<>(
                rate,
                HttpStatus.OK
        );
    }

    @Operation(summary = "Changes rate to inputted currencies")
    @ApiResponse(responseCode = "200", description = "Rate changed successfully", content = {@Content(schema = @Schema(implementation = RateResponseDTO.class), mediaType = "application/json")})
    @GetMapping("/convert")
    public ResponseEntity<RateResponseDTO> convertCurrency(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam double amount
    ) {
        return ResponseEntity.ok(userService.convertCurrency(from, to, amount));
    }

    @Operation(summary = "Update user based on the auth token")
    @ApiResponse(responseCode = "200", description = "User updated successfully", content = {@Content(schema = @Schema(implementation = ApiResponseDTO.class), mediaType = "application/json")})
    @PostMapping("/update-user")
    public ResponseEntity<ApiResponseDTO> updateUser(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateUserDTO updateUserDTO
    ) {
        this.userService.updateUser(token, updateUserDTO);
        return ResponseEntity.ok(new ApiResponseDTO(
                "User updated successfully",
                HttpStatus.OK,
                LocalDateTime.now()
        ));
    }
}