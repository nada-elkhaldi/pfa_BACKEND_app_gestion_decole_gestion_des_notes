package com.example.mySpringProject.service.impl;

import com.example.mySpringProject.model.Email;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String defaultSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendMail(Email email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String sender = email.getFrom() != null && !email.getFrom().isEmpty() ? email.getFrom() : defaultSender;
            System.out.println("Sender email: " + sender);
            helper.setFrom("SGSM-DPDPM 2024<" + sender + ">");
            helper.setTo(email.getRecipient());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody(), true);

            javaMailSender.send(message);

            return "Email sent";
        } catch (Exception e) {
            e.printStackTrace();
            return "Email not sent";
        }
    }
}
