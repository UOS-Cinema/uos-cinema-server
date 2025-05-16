package com.uos.dsd.cinema.domain.constraint;

public class UsernameConstraint {

    /*
     * Checks if the username is valid.
     * - 6 <= length <= 20
     * - Only alphanumeric characters are allowed
     */
    public static boolean isValidUsername(String username) {

        if (username.length() < 6 || username.length() > 20) {
            return false;
        }
        return username.matches("^[a-zA-Z0-9]+$");
    }
}
