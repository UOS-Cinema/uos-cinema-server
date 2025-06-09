package com.uos.dsd.cinema.domain.common.exception;

public class IllegalPasswordException extends IllegalArgumentException {

    public static final String MESSAGE = "비밀번호는 8자 이상 20자 이하이며, 영문자, 숫자, 특수문자를 포함해야 합니다.";

    public IllegalPasswordException() {
        super(MESSAGE);
    }
}
