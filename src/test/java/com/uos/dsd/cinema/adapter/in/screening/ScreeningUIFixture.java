package com.uos.dsd.cinema.adapter.in.screening;

import java.time.LocalDateTime;

import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningUpdateRequest;

public class ScreeningUIFixture {

    public static Long directorId = 1L;
    public static Long theaterId = 1L;
    public static String screenType = "2D";
    public static LocalDateTime startTime = LocalDateTime.now().plusDays(1);

    public static Long movieId = 1L;

    public static ScreeningCreateRequest screeningCreateRequest = new ScreeningCreateRequest(
        movieId,
        theaterId,
        screenType,
        startTime
    );

    public static ScreeningUpdateRequest screeningUpdateRequest = new ScreeningUpdateRequest(
        LocalDateTime.now().plusDays(1)
    );
}
