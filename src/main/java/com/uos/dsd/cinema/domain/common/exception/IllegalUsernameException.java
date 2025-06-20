package com.uos.dsd.cinema.domain.common.exception;

public class IllegalUsernameException extends IllegalArgumentException {

    public static final String MESSAGE = "아이디는 6자 이상 20자 이하이며, 영문자와 숫자만 포함해야 합니다.";

    public IllegalUsernameException() {
        super(MESSAGE);
    }
}
