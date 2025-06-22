package com.uos.dsd.cinema.acceptance.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.acceptance.theater.steps.TheaterSteps;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import java.util.List;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class TheaterAcceptanceTest extends AcceptanceTest {

    @Test
    public void getAllTheatersAcceptanceTest() {

        /* When */
        Response response = TheaterSteps.getAllTheaters();
        ApiResponse<List<TheaterResponse>> apiResponse =
            response.as(new TypeRef<ApiResponse<List<TheaterResponse>>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // theater size is greater than 0
        assertTrue(apiResponse.data().size() > 0);
    }

}