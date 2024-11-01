package com.banquemisr.moneytransactionservice.service;

import jakarta.mail.MessagingException;

public interface IEmail {


    /**
     * send html message to a specific user
     * @param to email of the recipient
     * @param subject the title of the email
     * @param messageBody the actual content of the email
     * @throws MessagingException The base class for all exceptions thrown by the Messaging classes
     * @author Ahmed Abd-Elwahed
     */
    void sendHtmlMessage(String username, String to, String subject, String messageBody) throws MessagingException;
}
