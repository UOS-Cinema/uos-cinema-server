package com.uos.dsd.cinema.domain.constraint;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

public class UsernameConstraint {

    private static final int MIN_LENGTH = 6;
    private static final int MAX_LENGTH = 20;
    private static final String ALLOWED_CHARS_REGEX = "^[a-zA-Z0-9]+$";

    public static void validateUsername(String username) {

        if (!isValidUsername(username)) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST,
                "아이디는 6자 이상 20자 이하이며, 영문자와 숫자만 포함해야 합니다.");
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
