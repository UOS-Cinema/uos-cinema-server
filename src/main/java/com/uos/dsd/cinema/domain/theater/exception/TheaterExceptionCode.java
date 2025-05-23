package com.uos.dsd.cinema.domain.theater.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TheaterExceptionCode implements ResultCode {

    THEATER_NOT_FOUND("THE001", "Theater not found: %s"),
    THEATER_ALREADY_EXISTS("THE002", "Theater already exists: %s %s"),
    ;

    private final String code;
    private final String message;
}
