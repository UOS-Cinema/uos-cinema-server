package com.uos.dsd.cinema.acceptance.point;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.point.steps.PointSteps;
import com.uos.dsd.cinema.application.port.in.point.response.PointHistoryResponse;
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
public class PointAcceptanceTest extends AcceptanceTest {

    @Test
    void 회원_포인트_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When
        Response response = PointSteps.sendGetPoint(headers);
        log.info("response: {}", response.asString());
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        // 최종 포인트: 1120 (400 + 900 - 500 + 220)
        assertEquals(1120, apiResponse.data());
    }

    @Test
    void 회원_포인트_내역_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);
        
        // When
        Response response = PointSteps.sendGetPointHistory(headers, 0, 10);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<PointHistoryResponse> content = apiResponse.data().content();
        assertEquals(5, content.size()); // 5개의 포인트 거래 내역
        
        // 최신 내역부터 확인 (적립)
        assertEquals(301L, content.get(0).paymentId());
        assertEquals(220, content.get(0).point());
        assertEquals(1120, content.get(0).totalPoint());
        
        // 사용
        assertEquals(301L, content.get(1).paymentId());
        assertEquals(-500, content.get(1).point());
        assertEquals(900, content.get(1).totalPoint());
    }

    @Test
    void 회원_포인트_내역_페이징_조회() {
        // Given
        String accessToken = loginMember();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);
        
        // When - 페이지 크기 2로 조회
        Response response = PointSteps.sendGetPointHistory(headers, 0, 2);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkSuccess(response.statusCode(), apiResponse.code());
        assertNotNull(apiResponse.data());
        List<PointHistoryResponse> content = apiResponse.data().content();
        assertEquals(2, content.size());
        assertEquals(0, apiResponse.data().page());
        assertEquals(2, apiResponse.data().size());
        assertEquals(true, apiResponse.data().hasNext());
    }

    @Test
    void 비회원_포인트_조회_실패() {
        // Given
        String accessToken = loginGuest();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // When
        Response response = PointSteps.sendGetPoint(headers);
        log.info("response: {}", response.asString());
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkForbidden(response.statusCode(), apiResponse.code());
    }

    @Test
    void 비회원_포인트_내역_조회_실패() {
        // Given
        String accessToken = loginGuest();
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);
        
        // When
        Response response = PointSteps.sendGetPointHistory(headers, 0, 10);
        log.info("response: {}", response.asString());
        ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>> apiResponse = 
            response.as(new TypeRef<ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // Then
        checkForbidden(response.statusCode(), apiResponse.code());
    }

    private void checkSuccess(int statusCode, String code) {
        assertEquals(200, statusCode);
        assertEquals(CommonResultCode.SUCCESS.getCode(), code);
    }

    private void checkForbidden(int statusCode, String code) {
        assertEquals(403, statusCode);
        assertEquals(CommonResultCode.FORBIDDEN.getCode(), code);
    }
} 
