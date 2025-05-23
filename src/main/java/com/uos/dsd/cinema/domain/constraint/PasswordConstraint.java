package com.uos.dsd.cinema.domain.constraint;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

public class PasswordConstraint {

    private static final int MIN_LENGTH = 8;
    private static final int MAX_LENGTH = 20;
    private static final String SPECIAL_CHARS = "!@#$%^&*()-_=+[]{}|;:,.<>/?";

    public static void validatePassword(String password) {
        
        if (!isValidPassword(password)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST,
                "비밀번호는 8자 이상 20자 이하이며, 영문자, 숫자, 특수문자를 포함해야 합니다.");
        }
    }

    /*
     * Checks if the password is valid.
     * - 8 <= length <= 20
     * - At least one letter
     * - At least one digit
     * - At least one special character
     * - Allowed characters: a-z, A-Z, 0-9, !@#$%^&*()-_=+[]{}|;:,.<>/?
     */
    public static boolean isValidPassword(String password) {

        if (password.length() < MIN_LENGTH || password.length() > MAX_LENGTH) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (SPECIAL_CHARS.contains(String.valueOf(c))) {
                hasSpecialChar = true;
            } else {
                return false;
            }
        }

        return hasLetter && hasDigit && hasSpecialChar;
    }
}
