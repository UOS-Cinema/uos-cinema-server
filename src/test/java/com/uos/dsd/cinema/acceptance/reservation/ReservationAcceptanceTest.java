package com.uos.dsd.cinema.acceptance.reservation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.reservation.steps.ReservationSteps;
import com.uos.dsd.cinema.adapter.in.reservation.ReservationUIFixture;
import com.uos.dsd.cinema.adaptor.in.web.reservation.request.ReserveRequest;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.domain.reservation.exception.ReservationExceptionCode;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ReservationAcceptanceTest extends AcceptanceTest {

    private final String guestAccessToken = loginGuest();
    private final Map<String, Object> guestHeaders =
        AuthHeaderProvider.createAuthorizationHeader(guestAccessToken);

    @Test
    void reserveSuccessTest() {

        /* given */
        ReserveRequest request = ReservationUIFixture.reserveRequest();

        /* when */
        Response response = ReservationSteps.reserve(guestHeaders, request);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        /* then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: SUCCESS
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertNotNull(apiResponse.data());
    }

    @Test
    void reservePassedScreeningTest() {
        /* given */
        ReserveRequest request = ReservationUIFixture.reservePassedScreeningRequest();

        /* when */
        Response response = ReservationSteps.reserve(guestHeaders, request);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        /* then */
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: COM000
        assertEquals(ReservationExceptionCode.LAST_ENTRANCES_TIME_EXCEEDED.getCode(),
                apiResponse.code());
        // message: LAST_ENTRANCES_TIME_EXCEEDED
        assertEquals(ReservationExceptionCode.LAST_ENTRANCES_TIME_EXCEEDED.getMessage(),
                apiResponse.message());
    }
    
    @Test
    void reserveDuplicateSeatTest() {

        /* given */
        ReserveRequest request = ReservationUIFixture.reserveRequest();
        ReservationSteps.reserve(guestHeaders, request);

        /* when */
        Response response = ReservationSteps.reserve(guestHeaders, request);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        /* then */
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: RSV002
        assertEquals(ReservationExceptionCode.ALREADY_RESERVED_SEAT.getCode(), apiResponse.code());
        // message: Already reserved seat
        assertEquals(ReservationExceptionCode.ALREADY_RESERVED_SEAT.getMessage(), apiResponse.message());
    }
}
