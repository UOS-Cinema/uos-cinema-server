package com.uos.dsd.cinema.domain.customer.constraint;

import com.uos.dsd.cinema.domain.customer.exception.IllegalNameException;

public class NameConstraint {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 50;
    private static final String ALLOWED_CHARS_REGEX = "^[가-힣a-zA-Z\\s]+$";

    public static void validateName(String name) {
        
        if (!isValidName(name)) {
            throw new IllegalNameException();
        }
    }

    /*
     * Checks if the name is valid.
     * - 2 <= length <= 50
     * - Allow characters: 한글(가-힣), 영문자(a-z, A-Z), 공백
     * - 앞뒤 공백은 허용하지 않음
     */
    public static boolean isValidName(String name) {

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        String trimmedName = name.trim();
        
        if (trimmedName.length() < MIN_LENGTH || trimmedName.length() > MAX_LENGTH) {
            return false;
        }

        // 연속된 공백이 있는지 확인
        if (trimmedName.contains("  ")) {
            return false;
        }

        return trimmedName.matches(ALLOWED_CHARS_REGEX);
    }
} 
