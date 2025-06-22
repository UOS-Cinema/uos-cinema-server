package com.uos.dsd.cinema.domain.screening.constraint;

public class ScreeningConstraint {

    public static final int MIN_DURATION = 30;
    public static final int MAX_DURATION = 360;

    public static boolean isInvalidDuration(int duration) {
        return duration < MIN_DURATION || duration > MAX_DURATION;
    }
}
