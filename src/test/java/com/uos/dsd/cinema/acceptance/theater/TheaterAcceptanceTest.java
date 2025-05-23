package com.uos.dsd.cinema.acceptance.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.theater.TheaterFixture;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.acceptance.theater.steps.TheaterSteps;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class TheaterAcceptanceTest extends AcceptanceTest {

    @Test
    public void createTheaterAcceptanceTest() {

        /* Given */
        TheaterCreateRequest request = TheaterFixture.getTheaterCreateRequest();
        Long theaterNumber = TheaterFixture.getTheaterNumber();
        String theaterName = TheaterFixture.getTheaterName();
        List<List<LayoutElement>> layout = TheaterFixture.getLayout();
        List<String> screenTypes = TheaterFixture.getScreenTypes();

        /* When */
        // 1. Store access token after admin login
        String accessToken = "temp-access-token";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // 2. Create theater
        Response response = TheaterSteps.postTheater(headers, request);
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);
        Response theaterResponse = TheaterSteps.getTheater(headers, theaterNumber);
        ApiResponse<TheaterResponse> theaterApiResponse =
            theaterResponse.as(new TypeRef<ApiResponse<TheaterResponse>>() {});
        log.info("TheaterApiResponse: {}", theaterApiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data: 10
        assertEquals(10, apiResponse.data());

        // theater number is 10
        assertEquals(theaterNumber, theaterApiResponse.data().number());
        // theater name is "Theater Name"
        assertEquals(theaterName, theaterApiResponse.data().name());
        // layout is the same
        assertEquals(layout, theaterApiResponse.data().layout());
        // screen types are the same
        assertEquals(screenTypes, theaterApiResponse.data().screenTypes());
    }

    @Test
    public void getTheaterAcceptanceTest() {

        /* Given */
        Long theaterNumber = 1L;
        String theaterName = "1관";
        List<List<LayoutElement>> layout = Arrays.asList(
            Arrays.asList(LayoutElement.NONE, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.SEAT, LayoutElement.NONE),
            Arrays.asList(LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.AISLE,
                LayoutElement.SEAT, LayoutElement.SEAT),
            Arrays.asList(LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.AISLE,
                LayoutElement.SEAT, LayoutElement.SEAT)
        );
        List<String> screenTypes = Arrays.asList("2D", "3D", "4D");

        /* When */
        // 1. Store access token after admin login
        String accessToken = "temp-access-token";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // 2. Get theater
        Response response = TheaterSteps.getTheater(headers, theaterNumber);
        ApiResponse<TheaterResponse> apiResponse =
            response.as(new TypeRef<ApiResponse<TheaterResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // theater number is 1
        assertEquals(theaterNumber, apiResponse.data().number());
        // theater name is "1관"
        assertEquals(theaterName, apiResponse.data().name());
        // layout is the same
        assertEquals(layout, apiResponse.data().layout());
        // screen types are the same
        assertEquals(screenTypes, apiResponse.data().screenTypes());
    }

    @Test
    public void updateTheaterAcceptanceTest() {

        /* Given */
        Long theaterNumber = 1L;
        TheaterUpdateRequest request = TheaterFixture.getTheaterModifyRequest();
        String theaterName = TheaterFixture.getUpdateTheaterName();
        List<List<LayoutElement>> layout = TheaterFixture.getUpdateLayout();
        List<String> screenTypes = TheaterFixture.getUpdateScreenTypes();

        /* When */
        // 1. Store access token after admin login
        String accessToken = "temp-access-token";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // 2. Update theater
        Response response = TheaterSteps.updateTheater(theaterNumber, headers, request);
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);
        Response theaterResponse = TheaterSteps.getTheater(headers, theaterNumber);
        ApiResponse<TheaterResponse> theaterApiResponse =
            theaterResponse.as(new TypeRef<ApiResponse<TheaterResponse>>() {});
        log.info("TheaterApiResponse: {}", theaterApiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data: 1
        assertEquals(1, apiResponse.data());

        // theater number is 1
        assertEquals(theaterNumber, theaterApiResponse.data().number());
        // theater name is "Changed Theater Name"
        assertEquals(theaterName, theaterApiResponse.data().name());
        // layout is the same
        assertEquals(layout, theaterApiResponse.data().layout());
        // screen types are the same
        assertEquals(screenTypes, theaterApiResponse.data().screenTypes());
    }

    @Test
    public void deleteTheaterAcceptanceTest() {

        /* Given */
        Long theaterNumber = 1L;

        /* When */
        // 1. Store access token after admin login
        String accessToken = "temp-access-token";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // 2. Delete theater
        Response response = TheaterSteps.deleteTheater(theaterNumber, headers);
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data: null
        assertNull(apiResponse.data());
    }
}