package com.banquemisr.moneytransactionservice.service;

import com.banquemisr.moneytransactionservice.model.Account;
import com.banquemisr.moneytransactionservice.model.OTP;
import jakarta.mail.MessagingException;

public interface IOTP {

    /**
     * create a one time password using account number
     * @return one time password
     * @author Ahmed-Elwahed
     */
    OTP createOTP();

    /**
     * retrieve otp form the database using account number
     * @param account the account that provides the number
     * @return one time password
     * @author Ahmed-Elwahed
     */
    OTP findOTPByAccount(Account account);

    /**
     * verify the one time password
     * @param otp one time password to be verified
     * @param accountId account number in order to retrieve the otp
     * @author Ahmed-Elwahed
     */
    void verifyOTP(String otp, String accountId);

    /**
     * delete all OTPs by account
     * @param account account number in order to delete all related OTPs
     */
    void deleteOTPByAccount(Account account);

    /**
     * resend OTP again if not received
     * @param email the email address to resend the OTP to.
     * @param accountNumber the account number to generate the OTP to.
     * @throws MessagingException The base class for all exceptions thrown by the Messaging classes
     * @author Ahmed Abd-Elwahed
     */
    void resendOTP(String email, String accountNumber) throws MessagingException;
}
