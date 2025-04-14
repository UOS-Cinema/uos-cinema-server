package com.uos.dsd.cinema.common.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;

@Getter
public class CinemaException extends RuntimeException {

    private final ResultCode resultCode;

    public CinemaException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.resultCode = resultCode;
    }

    public CinemaException(ResultCode resultCode, String message) {
        super(message);
        this.resultCode = resultCode;
    }
    
    public CinemaException(ResultCode resultCode, Throwable cause) {
        super(cause);
        this.resultCode = resultCode;
    }

    public CinemaException(ResultCode resultCode, String message, Throwable cause) {
        super(message, cause);
        this.resultCode = resultCode;
    }
}
