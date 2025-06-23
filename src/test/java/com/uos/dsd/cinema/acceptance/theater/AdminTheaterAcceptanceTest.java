package com.uos.dsd.cinema.acceptance.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.theater.TheaterFixture;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.domain.theater.exception.TheaterExceptionCode;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.acceptance.theater.steps.AdminTheaterSteps;
import com.uos.dsd.cinema.acceptance.theater.steps.TheaterSteps;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import java.util.List;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminTheaterAcceptanceTest extends AcceptanceTest {

    private final String adminAccessToken = loginAdmin();

    @Test
    public void createTheaterAcceptanceTest() {

        /* Given */
        TheaterCreateRequest request = TheaterFixture.getTheaterCreateRequest();
        Long theaterNumber = TheaterFixture.getTheaterNumber();
        String theaterName = TheaterFixture.getTheaterName();
        List<List<LayoutElement>> layout = TheaterFixture.getLayout();
        List<String> screenTypes = TheaterFixture.getScreenTypes();

        /* When */
        // 1. Create theater
        Response response = AdminTheaterSteps.postTheater(
            AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), request);
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // 2. Get theater
        Response theaterResponse = TheaterSteps.getTheater(theaterNumber);
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
        // data: theater number
        assertEquals(theaterNumber, apiResponse.data());

        // theater number is the same
        assertEquals(theaterNumber, theaterApiResponse.data().number());
        // theater name is the same
        assertEquals(theaterName, theaterApiResponse.data().name());
        // layout is the same
        assertEquals(layout, theaterApiResponse.data().layout());
        // screen types are the same
        assertEquals(screenTypes, theaterApiResponse.data().screenTypes());
    }


    @Test
    public void updateTheaterSuccessAcceptanceTest() {

        /* Given */
        TheaterCreateRequest request = TheaterFixture.getTheaterCreateRequest();
        Response createResponse = AdminTheaterSteps.postTheater(
            AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), request);
        ApiResponse<Long> createApiResponse = createResponse.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("CreateApiResponse: {}", createApiResponse);
        Long theaterNumber = createApiResponse.data();
        String theaterName = TheaterFixture.getTheaterName();
        List<List<LayoutElement>> layout = TheaterFixture.getLayout();
        List<String> screenTypes = TheaterFixture.getScreenTypes();

        /* When */
        // 1. Update theater
        Response response = AdminTheaterSteps.updateTheater(
            theaterNumber, AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), TheaterFixture.getTheaterModifyRequest());
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // 2. Get theater
        Response theaterResponse = TheaterSteps.getTheater(theaterNumber);
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

        // theater number is the same
        assertEquals(theaterNumber, theaterApiResponse.data().number());
        // theater name is "Changed Theater Name"
        assertEquals(theaterName, theaterApiResponse.data().name());
        // layout is the same
        assertEquals(layout, theaterApiResponse.data().layout());
        // screen types are the same
        assertEquals(screenTypes, theaterApiResponse.data().screenTypes());
    }

    @Test
    public void updateTheaterFailedAcceptanceTest() {

        /* Given */
        Long theaterNumber = 1L;
        TheaterUpdateRequest request = TheaterFixture.getTheaterModifyRequest();

        /* When */
        // 1. Update theater
        Response response = AdminTheaterSteps.updateTheater(
            theaterNumber, AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), request);
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: THE004
        assertEquals(TheaterExceptionCode.THEATER_HAS_RUNNING_SCREENINGS.getCode(), apiResponse.code());
        // message: Theater has running screenings
        assertEquals(TheaterExceptionCode.THEATER_HAS_RUNNING_SCREENINGS.getMessage(), apiResponse.message());
    }

    @Test
    public void deleteTheaterFailedAcceptanceTest() {

        /* Given */
        Long theaterNumber = 1L;

        /* When */
        // 1. Delete theater
        Response response = AdminTheaterSteps.deleteTheater(
            theaterNumber, AuthHeaderProvider.createAuthorizationHeader(adminAccessToken));
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: THE004
        assertEquals(TheaterExceptionCode.THEATER_HAS_RUNNING_SCREENINGS.getCode(), apiResponse.code());
        // message: Theater has running screenings
        assertEquals(TheaterExceptionCode.THEATER_HAS_RUNNING_SCREENINGS.getMessage(), apiResponse.message());
    }


    @Test
    public void deleteTheaterSuccessAcceptanceTest() {

        /* Given */
        TheaterCreateRequest request = TheaterFixture.getTheaterCreateRequest();
        Response createResponse = AdminTheaterSteps.postTheater(
            AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), request);
        ApiResponse<Long> createApiResponse = createResponse.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("CreateApiResponse: {}", createApiResponse);
        Long theaterNumber = createApiResponse.data();

        /* When */
        // 1. Delete theater
        Response response = AdminTheaterSteps.deleteTheater(
            theaterNumber, AuthHeaderProvider.createAuthorizationHeader(adminAccessToken));
        ApiResponse<Integer> apiResponse = response.as(new TypeRef<ApiResponse<Integer>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // 2. Get theater
        Response theaterResponse = TheaterSteps.getTheater(theaterNumber);
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
        // data: null
        assertNull(apiResponse.data());
        // get theater response is 404
        assertEquals(404, theaterResponse.statusCode());
        // get theater api response is 404
        assertEquals(TheaterExceptionCode.THEATER_NOT_FOUND.getCode(), theaterApiResponse.code());
    }
}