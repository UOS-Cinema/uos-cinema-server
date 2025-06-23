package com.uos.dsd.cinema.acceptance.payment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.payment.steps.PaymentSteps;
import com.uos.dsd.cinema.acceptance.reservation.steps.ReservationSteps;
import com.uos.dsd.cinema.adapter.in.reservation.ReservationUIFixture;
import com.uos.dsd.cinema.adaptor.in.web.payment.request.PaymentRequest;
import com.uos.dsd.cinema.adaptor.in.web.reservation.request.ReserveRequest;
import com.uos.dsd.cinema.domain.payment.PaymentMethod;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.domain.reservation.exception.ReservationExceptionCode;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class PaymentAcceptanceTest extends AcceptanceTest {

    private final String memberAccessToken = loginMember();
    private final Map<String, Object> memberHeaders =
        AuthHeaderProvider.createAuthorizationHeader(memberAccessToken);

    @Test
    void paymentSuccessTest() {
        /* given */
        // 1. 먼저 예매를 생성
        ReserveRequest reserveRequest = ReservationUIFixture.reserveRequest();
        Response reserveResponse = ReservationSteps.reserve(memberHeaders, reserveRequest);
        ApiResponse<String> reserveApiResponse = reserveResponse.as(new TypeRef<ApiResponse<String>>() {});
        Long reservationId = Long.valueOf(reserveApiResponse.data());
        
        // 2. 결제 요청 생성
        PaymentRequest request = new PaymentRequest(
            reservationId,
            reserveRequest.customerCount(),
            PaymentMethod.CARD,    // paymentMethod
            "토스카드",            // affiliateName
            1000                   // usedPoint
        );

        /* when */
        Response response = PaymentSteps.payment(memberHeaders, request);
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
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
    void paymentWithInvalidReservationTest() {
        /* given */
        PaymentRequest request = new PaymentRequest(
            999999L,               // 존재하지 않는 예매 ID
            Map.of("ADULT", 2),    // customerCount
            PaymentMethod.CARD,    // paymentMethod
            "삼성카드",            // affiliateName
            1000                   // usedPoint
        );

        /* when */
        Response response = PaymentSteps.payment(memberHeaders, request);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        /* then */
        // status code: 404
        assertEquals(404, response.statusCode());
        // code: RSV001
        assertEquals(ReservationExceptionCode.RESERVATION_NOT_FOUND.getCode(), apiResponse.code());
        // message: RESERVATION_NOT_FOUND
        assertEquals(ReservationExceptionCode.RESERVATION_NOT_FOUND.getMessage(), apiResponse.message());
    }

    @Test
    void paymentWithInvalidCustomerTest() {
        /* given */
        // 다른 사용자의 예매 ID를 사용
        // 1. 먼저 예매를 생성
        ReserveRequest reserveRequest = ReservationUIFixture.reserveRequest();
        Response reserveResponse = ReservationSteps.reserve(memberHeaders, reserveRequest);
        ApiResponse<String> reserveApiResponse = reserveResponse.as(new TypeRef<ApiResponse<String>>() {});
        Long reservationId = Long.valueOf(reserveApiResponse.data());
        
        // 2. 결제 요청 생성
        PaymentRequest request = new PaymentRequest(
            reservationId,
            reserveRequest.customerCount(),
            PaymentMethod.CARD,    // paymentMethod
            "토스카드",            // affiliateName
            1000                   // usedPoint
        );
        Map<String, Object> guestHeaders = AuthHeaderProvider.createAuthorizationHeader(loginGuest());

        /* when */
        Response response = PaymentSteps.payment(guestHeaders, request);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        /* then */
        // status code: 401 또는 403
        assertEquals(401, response.statusCode());
        // code: COM001
        assertEquals(CommonResultCode.UNAUTHORIZED.getCode(), apiResponse.code());
    }
}
