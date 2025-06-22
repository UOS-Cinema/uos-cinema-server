package com.uos.dsd.cinema.acceptance.screening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import com.uos.dsd.cinema.acceptance.screening.steps.ScreeningAdminSteps;
import com.uos.dsd.cinema.adapter.in.screening.ScreeningUIFixture;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.screening.steps.ScreeningSteps;
import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningUpdateRequest;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ScreeningAdminAcceptanceTest extends AcceptanceTest {

    private String adminAccessToken = loginAdmin();

    @Test
    public void createScreening() {

        /* Given */
        ScreeningCreateRequest request = ScreeningUIFixture.screeningCreateRequest;

        /* When */
        // create screening
        Response response = ScreeningAdminSteps.postScreening(
                AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), request);
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);
        
        // get screening
        ScreeningResponse screeningResponse = getScreeningResponse(apiResponse.data());

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertNotNull(apiResponse.data());
        // movie id
        assertEquals(request.movieId(), screeningResponse.movieId());
        // theater id
        assertEquals(request.theaterId(), screeningResponse.theaterId());
        // screen type
        assertEquals(request.screenType(), screeningResponse.screenType());
        // start time
        assertEquals(request.startTime(), screeningResponse.startTime());
    }

    @Test
    public void updateScreening() {

        /* Given */
        Long id = 1L;
        ScreeningUpdateRequest updateRequest = ScreeningUIFixture.screeningUpdateRequest;

        /* When */
        // create screening
        Response response = ScreeningAdminSteps.putScreening(
                AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), id, updateRequest);
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        // get screening
        ScreeningResponse screeningResponse = getScreeningResponse(id);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertNotNull(apiResponse.data());
        // start time
        assertEquals(updateRequest.startTime(), screeningResponse.startTime());
    }

    private ScreeningResponse getScreeningResponse(Long id) {
        String guestAccessToken = loginGuest();
        Response response = ScreeningSteps.getScreening(
                AuthHeaderProvider.createAuthorizationHeader(guestAccessToken), id);
        ApiResponse<ScreeningResponse> apiResponse =
                response.as(new TypeRef<ApiResponse<ScreeningResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);
        return apiResponse.data();
    }
}
