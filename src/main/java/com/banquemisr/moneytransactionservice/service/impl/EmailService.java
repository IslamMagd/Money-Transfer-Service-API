package com.banquemisr.moneytransactionservice.service.impl;

import com.banquemisr.moneytransactionservice.service.IEmail;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.MessagingException;

import java.nio.charset.StandardCharsets;


@Service
@RequiredArgsConstructor
public class EmailService implements IEmail {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    @Override
    @Async
    public void sendHtmlMessage(String username, String to, String subject, String messageBody) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper =
                new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
        messageHelper.setTo(to);
        messageHelper.setSubject(subject);

        Context context = new Context();
        context.setVariable("username", username);
        context.setVariable("messageBody", messageBody);

        String html = templateEngine.process("pages/email_template.html", context);
        messageHelper.setText(html, true);
        messageHelper.addInline("logoImage", new ClassPathResource("static/assets/images/logo.png"));
        messageHelper.addInline("banner", new ClassPathResource("static/assets/images/banner2.jpg"));
        messageHelper.addInline("instagram", new ClassPathResource("static/assets/images/instagram.png"));
        messageHelper.addInline("facebook", new ClassPathResource("static/assets/images/facebook.png"));
        mailSender.send(message);

    }
}
