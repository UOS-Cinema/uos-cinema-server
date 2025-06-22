package com.uos.dsd.cinema.acceptance.screening;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.screening.steps.ScreeningReservationSteps;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class ScreeningReservationAcceptanceTest extends AcceptanceTest {

    @Test
    public void getScreeningReservationSeatingStatus() {
        
        /* Given */
        Long screeningId = 1L;
        String guestAccessToken = loginGuest();

        /* When */
        Response response = ScreeningReservationSteps.getScreeningReservationSeatingStatus(
            AuthHeaderProvider.createAuthorizationHeader(guestAccessToken),
                screeningId);
        ApiResponse<List<List<LayoutElement>>> apiResponse =
                response.as(new TypeRef<ApiResponse<List<List<LayoutElement>>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data: not empty
        assertTrue(apiResponse.data().size() > 0);
    }
}
