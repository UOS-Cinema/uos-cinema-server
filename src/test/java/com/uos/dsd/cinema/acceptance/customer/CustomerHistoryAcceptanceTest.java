package com.uos.dsd.cinema.acceptance.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.customer.member.steps.MemberSteps;
import com.uos.dsd.cinema.acceptance.customer.steps.CustomerHistorySteps;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.MemberLoginResponse;
import com.uos.dsd.cinema.application.port.in.payment.response.PaymentHistoryResponse;
import com.uos.dsd.cinema.application.port.in.reservation.response.ReservationHistoryResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CustomerHistoryAcceptanceTest extends AcceptanceTest {

    // Test data from data.sql
    private static final String EXIST_MEMBER_USERNAME = "user1";
    private static final String EXIST_MEMBER_PASSWORD = "password123!";

    @Test
    void 회원_예매_내역_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When
        Response response = CustomerHistorySteps.sendGetReservationHistory(headers, 0, 10);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<ReservationHistoryResponse> content = apiResponse.data().content();
        assertEquals(5, content.size()); // 회원의 예매 내역 (1001, 1002, 1003, 2001, 2002)
        
        // 최신 예매부터 확인
        assertEquals(1003L, content.get(0).reservationId());
        assertEquals("COMPLETED", content.get(0).status()); // 완료된 예매
    }

    @Test
    void 회원_결제_내역_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When
        Response response = CustomerHistorySteps.sendGetPaymentHistory(headers, 0, 10);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<PaymentHistoryResponse> content = apiResponse.data().content();
        assertEquals(4, content.size()); // 회원의 결제 내역 (1001, 1002, 1003, 3001)
        
        // 최신 결제부터 확인
        assertEquals(1003L, content.get(0).paymentId());
        assertEquals(0, content.get(0).usedPoint());
        assertEquals(100, content.get(0).earnedPoint());
    }

    @Test
    void 예매_내역_페이징_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When - 페이지 크기 2로 조회
        Response response = CustomerHistorySteps.sendGetReservationHistory(headers, 0, 2);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<ReservationHistoryResponse> content = apiResponse.data().content();
        assertEquals(2, content.size());
        assertEquals(0, apiResponse.data().page());
        assertEquals(2, apiResponse.data().size());
        assertEquals(true, apiResponse.data().hasNext());
    }

    @Test
    void 결제_내역_페이징_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When - 페이지 크기 2로 조회
        Response response = CustomerHistorySteps.sendGetPaymentHistory(headers, 0, 2);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<PaymentHistoryResponse> content = apiResponse.data().content();
        assertEquals(2, content.size());
        assertEquals(0, apiResponse.data().page());
        assertEquals(2, apiResponse.data().size());
        assertEquals(true, apiResponse.data().hasNext());
    }

    @Test
    void 비회원_예매_내역_조회() {
        // Given
        String accessToken = loginGuest();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When
        Response response = CustomerHistorySteps.sendGetReservationHistory(headers, 0, 10);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<ReservationHistoryResponse> content = apiResponse.data().content();
        assertEquals(1, content.size()); // 비회원의 예매 내역 (2003)
        
        assertEquals(2003L, content.get(0).reservationId());
        assertEquals("COMPLETED", content.get(0).status()); // 완료된 예매
    }

    @Test
    void 비회원_결제_내역_조회() {
        // Given
        String accessToken = loginGuest();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When
        Response response = CustomerHistorySteps.sendGetPaymentHistory(headers, 0, 10);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<PaymentHistoryResponse> content = apiResponse.data().content();
        assertEquals(1, content.size()); // 비회원의 결제 내역 (3003)
        
        assertEquals(3003L, content.get(0).paymentId());
        assertEquals(0, content.get(0).usedPoint());
        assertEquals(0, content.get(0).earnedPoint());
    }

    private String loginMember() {
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendLoginMember(headers,
                new MemberLoginRequest(EXIST_MEMBER_USERNAME, EXIST_MEMBER_PASSWORD));
        ApiResponse<MemberLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberLoginResponse>>() {});
        return apiResponse.data().accessToken();
    }

    private void checkSuccess(int statusCode, String code) {
        assertEquals(200, statusCode);
        assertEquals(CommonResultCode.SUCCESS.getCode(), code);
    }
} 
