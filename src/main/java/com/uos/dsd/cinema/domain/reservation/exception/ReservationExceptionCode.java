package com.uos.dsd.cinema.domain.reservation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.uos.dsd.cinema.common.exception.code.ResultCode;

@Getter
@RequiredArgsConstructor
public enum ReservationExceptionCode implements ResultCode {

    RESERVATION_COMPLETION_TIME_LIMIT("RSV001", "Reservation completion time limit exceeded"),
    ALREADY_RESERVED_SEAT("RSV002", "Already reserved seat"),
    INVALID_SEAT_AND_CUSTOMER_COUNT("RSV003", "Seat and customer count must be the same"),
    INVALID_CUSTOMER_ID("RSV004", "Only the customer who made the reservation can cancel it"),
    RESERVATION_NOT_FOUND("RSV005", "Reservation not found"),
    LAST_ENTRANCES_TIME_EXCEEDED("RSV006", "Last entrances time exceeded"),
    ;

    private final String code;
    private final String message;
}
