package com.uos.dsd.cinema.domain.constraint;

public class PasswordConstraint {

    /*
     * Checks if the password is valid.
     * - 8 <= length <= 20
     * - At least one letter
     * - At least one digit
     * - At least one special character
     */
    public static boolean isValidPassword(String password) {

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
