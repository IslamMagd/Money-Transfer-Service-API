package com.banquemisr.moneytransactionservice.service.impl;

import com.banquemisr.moneytransactionservice.exception.custom.AccountNotFoundException;
import com.banquemisr.moneytransactionservice.exception.custom.OTPExpiredException;
import com.banquemisr.moneytransactionservice.exception.custom.OTPNotFoundException;
import com.banquemisr.moneytransactionservice.model.Account;
import com.banquemisr.moneytransactionservice.model.OTP;
import com.banquemisr.moneytransactionservice.repository.AccountRepository;
import com.banquemisr.moneytransactionservice.repository.OTPRepository;
import com.banquemisr.moneytransactionservice.service.IEmail;
import com.banquemisr.moneytransactionservice.service.IOTP;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class OTPService implements IOTP {

    private final OTPRepository otpRepository;
    private final AccountRepository accountRepository;
    private final IEmail emailService;

    @Value("${application.otp.expirationtime}")
    private String EXPIRATION_DURATION_MINUTES;


    @Transactional
    @Override
    public OTP createOTP() {
        return this.otpRepository.save(OTP.builder()
                .value(String.format("%06d", new SecureRandom().nextInt(100000)))
                .timestamp(LocalTime.now())
                .build());
    }

    @Override
    public OTP findOTPByAccount(Account account) {
        return otpRepository.findOTPByAccount(account).orElse(null);
    }

    @Transactional
    @Override
    public void verifyOTP(String otp, String accountNumber) {
        var account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                new AccountNotFoundException("Account Not Found With Number " + accountNumber));
        var otpObj = otpRepository.findOTPByAccountAndValue(account, otp).orElseThrow(() ->
                new OTPNotFoundException("OTP Not Found With Number " + otp));
        LocalTime currentTime = LocalTime.now();
        long elapsedMinutes = ChronoUnit.MINUTES.between(otpObj.getTimestamp(), currentTime);
        if (elapsedMinutes >= Long.parseLong(EXPIRATION_DURATION_MINUTES)) {
            throw new OTPExpiredException("OTP Is Expired");
        }
        account.setIsActive(true);
        accountRepository.save(account);
        deleteOTPByAccount(account);
    }

    @Transactional
    @Override
    public void deleteOTPByAccount(Account account) {
        otpRepository.deleteOTPByAccount(account);
    }

    @Transactional
    @Override
    public void resendOTP(String email, String accountNumber) throws MessagingException {
        var account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(() ->
                new AccountNotFoundException("Account not found with account number: " + accountNumber));
        deleteOTPByAccount(account);
        var otp = createOTP();
        account.setOtp(otp);
        accountRepository.save(account);
        emailService.sendHtmlMessage(account.getAccountName(),
                email,
                "<No Replay> activate your account",
                otp.getValue()
        );
    }
}
