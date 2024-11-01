package com.banquemisr.moneytransactionservice.repository;

import com.banquemisr.moneytransactionservice.model.Account;
import com.banquemisr.moneytransactionservice.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends JpaRepository<OTP, Long> {

    Optional<OTP> findOTPByAccount(Account account);
    Optional<OTP> findOTPByAccountAndValue(Account account, String vaString);
    void deleteOTPByAccount(Account account);
}
