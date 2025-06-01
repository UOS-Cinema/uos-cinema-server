package com.uos.dsd.cinema.domain.screening.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ScreeningExceptionCode implements ResultCode{

    MOVIE_NOT_FOUND("SCR001", "Movie not found"),
    THEATER_NOT_FOUND("SCR002", "Theater not found"),
    SCREEN_TYPE_NOT_FOUND("SCR003", "Screen type not found"),
    OVERLAPPING_START_TIME("SCR004", "Start time is overlapping"),
    DELETED_MOVIE("SCR005", "Movie is deleted"),
    IN_PAST_START_TIME("SCR006", "Start time can be created for the future"),
    SCREEN_TYPE_NOT_SUPPORTED_MOVIE("SCR007", "Screen type is not supported for the movie"),
    ONLY_ADMIN_CAN_ACCESS("SCR008", "Only admin can access"),
    ;

    private final String code;
    private final String message;
}
