package com.uos.dsd.cinema.domain.constraint;

import com.uos.dsd.cinema.domain.exception.IllegalPhoneException;

public class PhoneConstraint {

    private static final String PHONE_WITH_DASH_REGEX = "^010-\\d{4}-\\d{4}$";
    private static final String PHONE_WITHOUT_DASH_REGEX = "^010\\d{8}$";

    public static void validatePhone(String phone) {

        if (!isValidPhone(phone)) {
            throw new IllegalPhoneException();
        }
    }

    /*
     * Checks if the phone number is valid.
     * - Format 1: 010-0000-0000
     * - Format 2: 01000000000
     * - Only 010 prefix is allowed (Korean mobile numbers)
     */
    public static boolean isValidPhone(String phone) {

        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }

        String trimmedPhone = phone.trim();

        return trimmedPhone.matches(PHONE_WITH_DASH_REGEX) ||
                trimmedPhone.matches(PHONE_WITHOUT_DASH_REGEX);
    }
}
