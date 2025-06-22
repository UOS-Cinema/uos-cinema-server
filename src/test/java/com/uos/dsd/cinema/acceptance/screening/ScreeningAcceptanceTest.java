package com.uos.dsd.cinema.acceptance.screening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;
import java.util.List;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import com.uos.dsd.cinema.acceptance.screening.steps.ScreeningSteps;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ScreeningAcceptanceTest extends AcceptanceTest {

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3, 4, 5})
    public void getScreeningsByMovieId(Long movieId) {

        /* When */
        Response response = ScreeningSteps.getScreenings(
                AuthHeaderProvider.createEmptyHeader(),
                movieId, 
                null,
                null);
        ApiResponse<List<ScreeningResponse>> apiResponse =
                response.as(new TypeRef<ApiResponse<List<ScreeningResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertTrue(apiResponse.data().size() > 0);
        // movie id
        assertEquals(movieId, apiResponse.data().get(0).movieId());
    }

    @ParameterizedTest
    @ValueSource(longs = {1, 2, 3})
    public void getScreeningsByTheaterId(Long theaterId) {

        /* When */
        Response response = ScreeningSteps.getScreenings(
                AuthHeaderProvider.createEmptyHeader(),
                null, 
                theaterId,
                null);
        ApiResponse<List<ScreeningResponse>> apiResponse =
                response.as(new TypeRef<ApiResponse<List<ScreeningResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertNotNull(apiResponse.data());
        // theater id
        if (apiResponse.data().size() > 0) {
            assertEquals(theaterId, apiResponse.data().get(0).theaterId());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"2025-06-15", "2025-06-16", "2025-06-17", "2025-06-18", "2025-06-19",
                    "2025-06-20", "2025-06-21", "2025-06-22", "2025-06-23", "2025-06-24"})
    public void getScreeningsByDate(String date) {
        /* When */
        Response response = ScreeningSteps.getScreenings(
                AuthHeaderProvider.createEmptyHeader(),
                null, 
                null,
                date);
        ApiResponse<List<ScreeningResponse>> apiResponse =
                response.as(new TypeRef<ApiResponse<List<ScreeningResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertTrue(apiResponse.data().size() > 0);
        // date
        assertEquals(LocalDate.parse(date), apiResponse.data().get(0).startTime().toLocalDate());
    }

    @Test
    public void getScreeningsWithoutParams() {
        /* When */
        Response response = ScreeningSteps.getScreenings(
                AuthHeaderProvider.createEmptyHeader(),
                null, null, null);
        ApiResponse<List<ScreeningResponse>> apiResponse =
                response.as(new TypeRef<ApiResponse<List<ScreeningResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data is not null
        assertTrue(apiResponse.data().size() > 0);
        // date is today
        assertEquals(LocalDate.now(), apiResponse.data().get(0).startTime().toLocalDate());
    }
}
