package com.uos.dsd.cinema.common.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommonResultCode implements ResultCode {

    // success
    SUCCESS("COM000", "Success"),

    // failure
    BAD_REQUEST("COM001", "Bad Request"), 
    NOT_FOUND("COM002", "Not Found"),
    UNAUTHORIZED("COM003", "Unauthorized"),
    FORBIDDEN("COM004", "Forbidden"),
    INTERNAL_SERVER_ERROR("COM005", "Internal Server Error");

    private final String code;
    private final String message;
}
