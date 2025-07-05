package com.example.p2pTutoringSystem.Utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESUtil {
    private static final String SECRET_KEY = "1234567890123456";

    public static String encryptMessage(String message) {
        try {
            SecretKeySpec secretKey = new SecretKeySpec(SECRET_KEY.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error encrypting", e);
        }
    }

    public static String decryptMessage(String encryptedMessage) {
        if (encryptedMessage == null) {
            throw new IllegalArgumentException("Encrypted message cannot be null");
        }

        try {
            byte[] decodedKey = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec secretKey = new SecretKeySpec(decodedKey, "AES");

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            byte[] decodedMessage = Base64.getDecoder().decode(encryptedMessage.getBytes(StandardCharsets.UTF_8));
            byte[] decryptedBytes = cipher.doFinal(decodedMessage);

            return new String(decryptedBytes, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace(); // Consider using a logger
            return null; // Or rethrow depending on app logic
        }
    }
}

