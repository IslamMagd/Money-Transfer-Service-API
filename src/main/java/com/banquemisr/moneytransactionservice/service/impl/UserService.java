package com.banquemisr.moneytransactionservice.service.impl;

import com.banquemisr.moneytransactionservice.dto.*;
import com.banquemisr.moneytransactionservice.dto.enums.CurrencyRate;
import com.banquemisr.moneytransactionservice.exception.custom.AccountNotFoundException;
import com.banquemisr.moneytransactionservice.exception.custom.UserNotFoundException;
import com.banquemisr.moneytransactionservice.model.User;
import com.banquemisr.moneytransactionservice.repository.AccountRepository;
import com.banquemisr.moneytransactionservice.repository.UserRepository;
import com.banquemisr.moneytransactionservice.service.IUser;
import com.banquemisr.moneytransactionservice.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService implements IUser {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final JwtUtils jwtUtils;


    @Override
    public User getUserIfExistsById(Long userId) {
        return this.userRepository
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with user id %d", userId)));
    }

    @Override
    public User getUserIfExistsByEmail(String email) {
        return this.userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(String.format("User with user email %s not found", email)));
    }

    @Override
    public void updateUser(String token, UpdateUserDTO updateUserDTO) throws UserNotFoundException {
        String email = jwtUtils.getEmailFromHeader(token);
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));
        if (!(updateUserDTO.getUsername().isEmpty() || updateUserDTO.getUsername().isBlank())) {
            user.setUsername(updateUserDTO.getUsername());
        }
//        if (!(updateUserDTO.getPhone().isEmpty() || updateUserDTO.getPhone().isBlank())) {
//            user.setPhone(updateUserDTO.getPhone());
//        }
        if (updateUserDTO.getBirthdate() != null) {
            user.setBirthDate(updateUserDTO.getBirthdate());
        }
        if (updateUserDTO.getCountry() != null) {
            user.setCountry(updateUserDTO.getCountry());
        }
        userRepository.save(user);
    }

    @Override
    public UserResponseDTO getUser(String token) throws UserNotFoundException, AccountNotFoundException {
        String email = jwtUtils.getEmailFromHeader(token);
        var user = userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + email));
        var account = accountRepository.findAccountByUser(user).orElseThrow(() ->
                new AccountNotFoundException(String.format("Account with this user email %s not found", email)));

        return UserResponseDTO.builder()
                .id(user.getCustomerId())
                .fullname(user.getUsername())
                .email(user.getEmail())
                .country(user.getCountry())
                .dateOfBirth(user.getBirthDate())
                .accountNumber(account.getAccountNumber())
                .build();
    }

    @Override
    @Transactional
    public Double getRateToEGP(CurrencyRateDTO currencyRateDTO) {
        CurrencyRate currencyRate = CurrencyRate.fromCurrencyCode(currencyRateDTO.getCurrency());
        if (currencyRate == null) {
            return null;
        }
        return currencyRate.getRate() * currencyRateDTO.getAmount();
    }

    @Override
    @Transactional
    public Double getRateFromEGP(CurrencyRateDTO currencyRateDTO) {
        CurrencyRate currencyRate = CurrencyRate.fromCurrencyCode(currencyRateDTO.getCurrency());
        if (currencyRate == null) {
            return null;
        }
        return currencyRateDTO.getAmount() / currencyRate.getRate();
    }

    @Override
    @Transactional
    public Double getRate(CurrencyToFromRateDTO currencyToFromRateDTO) {
        Double fromAmount = this.getRateToEGP(CurrencyRateDTO.builder()
                .currency(currencyToFromRateDTO.getFromCurrency())
                .amount(currencyToFromRateDTO.getAmount())
                .build());
        return this.getRateFromEGP(CurrencyRateDTO.builder()
                .currency(currencyToFromRateDTO.getToCurrency())
                .amount(fromAmount)
                .build());
    }

    @Override
    public RateResponseDTO convertCurrency(String from, String to, double givenAmount) {

        var fromRate = Objects.requireNonNull(CurrencyRate.fromCurrencyCode(from)).getRate();
        var toRate = Objects.requireNonNull(CurrencyRate.fromCurrencyCode(to)).getRate();
        var rate = fromRate / toRate;
        var amount = givenAmount * rate;
        return RateResponseDTO.builder()
                .date(LocalDateTime.now())
                .from(from)
                .to(to)
                .givenAmount(givenAmount)
                .rate(rate)
                .convertedAmount(amount)
                .build();
    }


}
