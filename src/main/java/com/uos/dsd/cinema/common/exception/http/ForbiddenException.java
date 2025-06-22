package com.uos.dsd.cinema.common.exception.http;

import com.uos.dsd.cinema.common.exception.CinemaException;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.code.ResultCode;

public class ForbiddenException extends CinemaException {

    private static final ResultCode resultCode = CommonResultCode.FORBIDDEN;

    public ForbiddenException() {
        super(resultCode);
    }

    public ForbiddenException(ResultCode resultCode) {
        super(resultCode);
    }

    public ForbiddenException(String message) {
        super(resultCode, message);
    }

    public ForbiddenException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public ForbiddenException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }

    public ForbiddenException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
}
