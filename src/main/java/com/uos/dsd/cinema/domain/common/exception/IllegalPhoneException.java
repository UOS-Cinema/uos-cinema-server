package com.uos.dsd.cinema.domain.exception;

public class IllegalPhoneException extends IllegalArgumentException {

    public static final String MESSAGE = "전화번호는 010-0000-0000 또는 01000000000 형식이어야 합니다.";

    public IllegalPhoneException() {
        super(MESSAGE);
    }
} 
