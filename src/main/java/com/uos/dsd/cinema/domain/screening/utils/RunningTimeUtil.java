package com.uos.dsd.cinema.domain.screening.utils;

public class RunningTimeUtil {

    public static final int ADVERTISEMENT_TIME = 10;
    public static final int CLEANING_TIME = 10;
    public static final int WAITING_TIME = 10;

    public static int calculateRunningTime(int movieRunningTime) {

        return ADVERTISEMENT_TIME + movieRunningTime + CLEANING_TIME + WAITING_TIME;
    }
}
