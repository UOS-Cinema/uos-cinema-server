package com.uos.dsd.cinema.domain.screen_type.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ScreenTypeExceptionCode implements ResultCode {

    SCREEN_TYPE_NOT_FOUND("SCT001", "Screen type not found"),
    AT_LEAST_ONE_SCREEN_TYPE_REQUIRED("SCT002", "At least one screen type is required"),
    ;

    private final String code;
    private final String message;
    
}
