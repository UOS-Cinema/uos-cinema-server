package com.uos.dsd.cinema.domain.constraint;

import com.uos.dsd.cinema.domain.exception.IllegalBirthDateException;
import java.time.LocalDate;

public class BirthDateConstraint {

    private static final int MIN_YEAR = 1900;

    public static void validateBirthDate(LocalDate birthDate) {

        if (!isValidBirthDate(birthDate)) {
            throw new IllegalBirthDateException();
        }
    }

    /*
     * Checks if the birth date is valid.
     * - Year range: 1900 ~
     * - Cannot be future date
     */
    public static boolean isValidBirthDate(LocalDate birthDate) {

        if (birthDate == null) {
            return false;
        }

        LocalDate now = LocalDate.now();
        if (birthDate.getYear() < MIN_YEAR) {
            return false;
        }
        if (birthDate.isAfter(now)) {
            return false;
        }

        return true;
    }
}
