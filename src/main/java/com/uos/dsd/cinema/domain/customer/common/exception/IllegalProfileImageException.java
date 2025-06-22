package com.uos.dsd.cinema.domain.customer.common.exception;

public class IllegalProfileImageException extends IllegalArgumentException {

    public static final String MESSAGE = "프로필 이미지는 5MB 이하의 jpg, jpeg, png, gif, webp 파일만 허용됩니다.";

    public IllegalProfileImageException() {
        super(MESSAGE);
    }
} 
