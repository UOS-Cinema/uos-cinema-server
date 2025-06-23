package com.uos.dsd.cinema.domain.reservation.constraint;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationConstraint {

    COMPLETION_TIME_LIMIT(10),
    LAST_ENTRANCES_TIME(10);

    private final int minutes;

    public static boolean isExceeded(LocalDateTime createdAt) {
        return createdAt.isAfter(LocalDateTime.now().minusMinutes(COMPLETION_TIME_LIMIT.minutes));
    }

    public static boolean canEnter(LocalDateTime startTime) {
        LocalDateTime lastEntranceTime = startTime.minusMinutes(LAST_ENTRANCES_TIME.minutes);
        return LocalDateTime.now().isBefore(lastEntranceTime);
    }
}
