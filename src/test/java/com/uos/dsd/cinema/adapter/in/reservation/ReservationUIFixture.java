package com.uos.dsd.cinema.adapter.in.reservation;

import java.util.List;
import java.util.Map;

import com.uos.dsd.cinema.adaptor.in.web.reservation.request.ReserveRequest;

public class ReservationUIFixture {

    static Long screeningId = 40L;
    static Long theaterId = 1L;
    static List<String> seatNumbers = List.of("A1", "A2", "A3");
    static Map<String, Integer> customerCount = Map.of("ADULT", 2, "CHILD", 1);

    public static ReserveRequest reserveRequest() {
        return new ReserveRequest(screeningId, theaterId, seatNumbers, customerCount);
    }

    public static ReserveRequest reservePassedScreeningRequest() {
        return new ReserveRequest(1L, theaterId, seatNumbers, customerCount);
    }
}
