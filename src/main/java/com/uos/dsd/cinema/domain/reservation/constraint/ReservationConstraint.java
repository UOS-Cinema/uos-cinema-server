package com.uos.dsd.cinema.domain.reservation.constraint;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationConstraint {

    COMPLETION_TIME_LIMIT(10);

    private final int minutes;

    public static boolean isExceeded(LocalDateTime createdAt) {
        return createdAt.isAfter(LocalDateTime.now().minusMinutes(COMPLETION_TIME_LIMIT.minutes));
    }
}
