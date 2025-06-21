package com.uos.dsd.cinema.domain.movie.exception;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

@RequiredArgsConstructor
@Getter
public enum MovieExceptionCode implements ResultCode {

    MOVIE_NOT_FOUND("MOV001", "Movie not found"),
    DELETED_MOVIE("MOV002", "Movie is deleted"),
    ;

    private final String code;
    private final String message;
}
