package com.uos.dsd.cinema.domain.screen_type.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScreenTypeExceptionCode implements ResultCode {

    SCREEN_TYPE_NOT_FOUND("SCREEN_TYPE_NOT_FOUND", "Screen type not found"),
    ;

    private final String code;
    private final String message;
    
}
