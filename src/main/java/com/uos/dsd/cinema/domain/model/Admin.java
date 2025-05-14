package com.uos.dsd.cinema.domain.model;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.InternalServerErrorException;
import com.uos.dsd.cinema.common.model.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@Entity
@Table(name = "admins")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String password;

    public Admin(String name, String password) {

        setName(name);
        setPassword(password);
    }

    public boolean isPasswordMatched(String password) {

        // Decode the stored password
        byte[] storedSaltAndHash = Base64.getDecoder().decode(this.password);
        byte[] salt = new byte[16];
        System.arraycopy(storedSaltAndHash, 0, salt, 0, salt.length);

        // Hash the input password with the same salt
        String computedHash = hashPasswordWithSalt(password, salt);
        return this.password.equals(computedHash);
    }

    private void setName(String name) {

        if (name == null || (name.length() < 6 || name.length() > 20)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Invalid name format");
        }
        this.name = name;
    }

    private void setPassword(String password) {

        if (!isValidPassword(password)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Invalid password format");
        }
        this.password = hashPasswordWithSalt(password, generateSalt());
    }

    private byte[] generateSalt() {

        byte[] salt = new byte[16];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    private String hashPasswordWithSalt(String password, byte[] salt) {

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

    /*
     * Checks if the password is valid.
     * - 8 <= length <= 20
     * - At least one letter
     * - At least one digit
     * - At least one special character
     * - No spaces
     */
    private boolean isValidPassword(String password) {

        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>/?";

        for (char c : password.toCharArray()) {
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (specialChars.contains(String.valueOf(c))) {
                hasSpecialChar = true;
            } else {
                return false;
            }
        }

        return hasLetter && hasDigit && hasSpecialChar;
    }
}
