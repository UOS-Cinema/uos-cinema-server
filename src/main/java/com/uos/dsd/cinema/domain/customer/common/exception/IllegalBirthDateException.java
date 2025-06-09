package com.uos.dsd.cinema.domain.customer.common.exception;

public class IllegalBirthDateException extends IllegalArgumentException {

    public static final String MESSAGE = "생년월일은 YYYY-MM-DD 형식이며, 유효한 날짜여야 합니다.";

    public IllegalBirthDateException() {
        super(MESSAGE);
    }
} 
