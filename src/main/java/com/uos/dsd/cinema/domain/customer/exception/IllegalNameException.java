package com.uos.dsd.cinema.domain.customer.exception;

public class IllegalNameException extends IllegalArgumentException {

    public static final String MESSAGE = "이름은 2자 이상 50자 이하이며, 한글, 영문자, 공백만 허용됩니다.";

    public IllegalNameException() {
        super(MESSAGE);
    }
} 
