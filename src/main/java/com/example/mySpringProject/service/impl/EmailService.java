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
    private final JavaMailSenderImpl mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public EmailService(JavaMailSender javaMailSender, JavaMailSenderImpl mailSender) {
        this.javaMailSender = javaMailSender;
        this.mailSender = mailSender;
    }

    public String sendMail(Email email) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("ELKHALDI Nada<" + sender + ">");
            helper.setTo(email.getRecipient());
            helper.setSubject(email.getSubject());
            helper.setText(email.getBody());

            javaMailSender.send(message);

            return "Email sent";
        } catch (Exception e) {
            e.printStackTrace();
            return "Email not sent";
        }
    }
}

