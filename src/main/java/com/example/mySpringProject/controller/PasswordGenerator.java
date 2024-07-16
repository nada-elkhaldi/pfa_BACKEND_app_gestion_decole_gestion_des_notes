package com.example.mySpringProject.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 10;

    private final SecureRandom secureRandom;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordGenerator() {
        secureRandom = new SecureRandom();
        passwordEncoder = new BCryptPasswordEncoder();
    }

    public String generatePassword() {
        StringBuilder sb = new StringBuilder(PASSWORD_LENGTH);
        for (int i = 0; i < PASSWORD_LENGTH; i++) {
            int randomIndex = secureRandom.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    public String encryptPassword(String password) {
        return passwordEncoder.encode(password);
    }
}