package com.uos.dsd.cinema.common.exception.http;

import com.uos.dsd.cinema.common.exception.CinemaException;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.code.ResultCode;

public class NotFoundException extends CinemaException {

    private static final ResultCode resultCode = CommonResultCode.NOT_FOUND;

    public NotFoundException() {
        super(CommonResultCode.NOT_FOUND);
    }

    public NotFoundException(ResultCode resultCode) {
        super(resultCode);
    }

    public NotFoundException(String message) {
        super(resultCode, message);
    }

    public NotFoundException(ResultCode resultCode, String message) {
        super(resultCode, message);
    }

    public NotFoundException(ResultCode resultCode, Throwable cause) {
        super(resultCode, cause);
    }

    public NotFoundException(ResultCode resultCode, String message, Throwable cause) {
        super(resultCode, message, cause);
    }
}
