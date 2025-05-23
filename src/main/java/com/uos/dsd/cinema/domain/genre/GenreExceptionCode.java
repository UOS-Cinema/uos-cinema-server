package com.uos.dsd.cinema.domain.genre;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GenreExceptionCode implements ResultCode {

    GENRE_NOT_FOUND("GNR001", "Genre not found"),
    AT_LEAST_ONE_GENRE_REQUIRED("GNR002", "At least one genre is required"),
    INVALID_NAME("GNR003", "Genre name must be alphanumeric"),
    INVALID_NAME_LENGTH("GNR004", String.format(
        "Genre name must be less than %d characters", 
        GenreConstraint.NAME_MAX_LENGTH)),
    INVALID_DESCRIPTION_LENGTH("GNR005", String.format(
        "Genre description must be less than %d characters", 
        GenreConstraint.DESCRIPTION_MAX_LENGTH)),
    INVALID_IMAGE_URL_LENGTH("GNR006", String.format(
        "Genre image URL must be less than %d characters", 
        GenreConstraint.IMAGE_URL_MAX_LENGTH)),
    ;

    private final String code;
    private final String message;
}
