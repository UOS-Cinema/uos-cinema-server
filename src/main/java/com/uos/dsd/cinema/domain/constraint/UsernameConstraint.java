package com.uos.dsd.cinema.domain.constraint;

import com.uos.dsd.cinema.domain.exception.IllegalUsernameException;

public class UsernameConstraint {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private static final String ALLOWED_CHARS_REGEX = "^[a-zA-Z0-9]+$";

    public static void validateUsername(String username) {

        if (!isValidUsername(username)) {
            throw new IllegalUsernameException();
        }
    }

    /*
     * Checks if the username is valid.
     * - 6 <= length <= 20
     * - Allow characters: a-z, A-Z, 0-9
     */
    public static boolean isValidUsername(String username) {

        if (username.length() < MIN_LENGTH || username.length() > MAX_LENGTH) {
            return false;
        }
        return username.matches(ALLOWED_CHARS_REGEX);
    }
}
