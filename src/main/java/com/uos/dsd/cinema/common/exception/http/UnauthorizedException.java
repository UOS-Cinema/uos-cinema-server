package com.uos.dsd.cinema.common.exception.http;

import com.uos.dsd.cinema.common.exception.CinemaException;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.code.ResultCode;

public class UnauthorizedException extends CinemaException {

    private static final ResultCode DEFAULT_RESULT_CODE = CommonResultCode.UNAUTHORIZED;

    public UnauthorizedException() {
        super(DEFAULT_RESULT_CODE);
    }

    public UnauthorizedException(String message) {
        super(DEFAULT_RESULT_CODE, message);
    }

    public UnauthorizedException(ResultCode resultCode) {
        super(resultCode);
    }

    public UnauthorizedException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public UnauthorizedException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }

    public UnauthorizedException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
}
