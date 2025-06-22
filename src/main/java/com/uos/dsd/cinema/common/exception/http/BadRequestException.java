package com.uos.dsd.cinema.common.exception.http;

import com.uos.dsd.cinema.common.exception.CinemaException;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.code.ResultCode;

public class BadRequestException extends CinemaException {

    private static final ResultCode resultCode = CommonResultCode.BAD_REQUEST;

    public BadRequestException() {
        super(resultCode);
    }

    public BadRequestException(ResultCode resultCode) {
        super(resultCode);
    }

    public BadRequestException(String message) {
        super(resultCode, message);
    }

    public BadRequestException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public BadRequestException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }

    public BadRequestException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
}
