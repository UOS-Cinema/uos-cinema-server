package com.uos.dsd.cinema.domain.screening.exception;

import com.uos.dsd.cinema.common.exception.code.ResultCode;
import com.uos.dsd.cinema.domain.screening.constraint.ScreeningConstraint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ScreeningExceptionCode implements ResultCode{

    MOVIE_NOT_FOUND("SCR001", "Movie not found"),
    THEATER_NOT_FOUND("SCR002", "Theater not found"),
    SCREEN_TYPE_NOT_FOUND("SCR003", "Screen type not found"),
    SCREENING_NOT_FOUND("SCR004", "Screening not found"),
    OVERLAPPING_START_TIME("SCR005", "Start time is overlapping"),
    DELETED_MOVIE("SCR006", "Movie is deleted"),
    IN_PAST_START_TIME("SCR007", "Start time can be created for the future"),
    SCREEN_TYPE_NOT_SUPPORTED_MOVIE("SCR008", "Screen type is not supported for the movie"),
    ONLY_ADMIN_CAN_ACCESS("SCR009", "Only admin can access"),
    INVALID_DURATION("SCR010",
            String.format("Duration should be between %d and %d minutes",
                    ScreeningConstraint.MIN_DURATION, ScreeningConstraint.MAX_DURATION)),
    ;

    private final String code;
    private final String message;
}
