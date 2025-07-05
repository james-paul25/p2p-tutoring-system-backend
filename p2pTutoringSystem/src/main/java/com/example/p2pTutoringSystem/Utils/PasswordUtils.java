package com.example.p2pTutoringSystem.Utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtils {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String password) {
        return encoder.encode(password);
    }
    public static boolean verifyPassword(String rawPassword, String hashedPassword) {
        return encoder.matches(rawPassword, hashedPassword);
    }

    public static String encryptMessage(String message) {
        return encoder.encode(message);
    }

    public static boolean decryptMessage(String rawPassword, String encryptedMessage) {
        return encoder.matches(rawPassword, encryptedMessage);
    }

}
