package com.uos.dsd.cinema.common.utils;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.InternalServerErrorException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordUtil {

    public static byte[] extractSalt(String hashedPassword) {

        byte[] saltAndHash = Base64.getDecoder().decode(hashedPassword);
        byte[] salt = new byte[16];
        System.arraycopy(saltAndHash, 0, salt, 0, salt.length);
        return salt;
    }

    public static byte[] generateSalt() {

        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    public static String hashPasswordWithSalt(String password, byte[] salt) {

        try {
            // Hash password using PBKDF2
            int iteration = 10000;
            int keyLength = 256;
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, iteration, keyLength);
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hashedPassword = keyFactory.generateSecret(spec).getEncoded();

            // Combine salt and hashed password
            byte[] saltAndHash = new byte[salt.length + hashedPassword.length];
            System.arraycopy(salt, 0, saltAndHash, 0, salt.length);
            System.arraycopy(hashedPassword, 0, saltAndHash, salt.length, hashedPassword.length);

            // Encode to Base64 for storage
            return Base64.getEncoder().encodeToString(saltAndHash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new InternalServerErrorException(CommonResultCode.INTERNAL_SERVER_ERROR, "Error hashing password", e);
        }
    }
}
