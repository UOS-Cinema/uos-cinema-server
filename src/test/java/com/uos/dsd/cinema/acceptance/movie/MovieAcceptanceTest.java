package com.uos.dsd.cinema.acceptance.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.movie.steps.MovieSteps;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieElement;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.response.ApiResponse.PageResponse;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.adapter.in.movie.MovieUIFixture;
import com.uos.dsd.cinema.application.port.in.movie.query.MovieQueryCondition;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.Map;
import java.time.LocalDate;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class MovieAcceptanceTest extends AcceptanceTest {

    private final String GUEST_ACCESS_TOKEN = loginGuest();
    private final Map<String, Object> GUEST_HEADERS =
    AuthHeaderProvider.createAuthorizationHeader(GUEST_ACCESS_TOKEN);

    @Test
    public void getMovie() {

        /* Given */
        Long movieId = 1L;

        /* When */
        Response response = MovieSteps
                .sendGetMovie(GUEST_HEADERS, movieId);
        ApiResponse<MovieResponse> apiResponse = response.as(new TypeRef<ApiResponse<MovieResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);
        MovieResponse movieResponse = apiResponse.data();

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // movie id: 1
        assertEquals(movieId, movieResponse.id());
    }

    @Test
    public void getMovies() {

        /* Given */
        MovieQueryCondition condition1 = MovieUIFixture.defaultMovieQueryCondition;
        MovieQueryCondition condition2 = MovieUIFixture.thrillerQueryCondition;
        MovieQueryCondition condition3 = MovieUIFixture.startDateQueryCondition(LocalDate.now());
        MovieQueryCondition condition4 = MovieUIFixture.twoDQueryCondition;
        MovieQueryCondition condition5 = MovieUIFixture.sortByReleaseDateQueryCondition;

        /* When */
        Response response1 = MovieSteps.sendSearchMovies(GUEST_HEADERS, condition1);
        Response response2 = MovieSteps.sendSearchMovies(GUEST_HEADERS, condition2);
        Response response3 = MovieSteps.sendSearchMovies(GUEST_HEADERS, condition3);
        Response response4 = MovieSteps.sendSearchMovies(GUEST_HEADERS, condition4);
        Response response5 = MovieSteps.sendSearchMovies(GUEST_HEADERS, condition5);
        ApiResponse<PageResponse<MovieElement>> apiResponse1 =
            response1.as(new TypeRef<ApiResponse<PageResponse<MovieElement>>>() {});
        ApiResponse<PageResponse<MovieElement>> apiResponse2 =
            response2.as(new TypeRef<ApiResponse<PageResponse<MovieElement>>>() {});
        ApiResponse<PageResponse<MovieElement>> apiResponse3 =
            response3.as(new TypeRef<ApiResponse<PageResponse<MovieElement>>>() {});
        ApiResponse<PageResponse<MovieElement>> apiResponse4 =
                response4.as(new TypeRef<ApiResponse<PageResponse<MovieElement>>>() {});
        ApiResponse<PageResponse<MovieElement>> apiResponse5 =
            response5.as(new TypeRef<ApiResponse<PageResponse<MovieElement>>>() {});
        log.info("ApiResponse1: {}", apiResponse1);
        log.info("ApiResponse2: {}", apiResponse2);
        log.info("ApiResponse3: {}", apiResponse3);
        log.info("ApiResponse4: {}", apiResponse4);
        log.info("ApiResponse5: {}", apiResponse5);

        /* Then */
        // status code: 200
        assertEquals(200, response1.statusCode());
        assertEquals(200, response2.statusCode());
        assertEquals(200, response3.statusCode());
        assertEquals(200, response4.statusCode());
        assertEquals(200, response5.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse1.code());
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse2.code());
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse3.code());
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse4.code());
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse5.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse1.message());
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse2.message());
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse3.message());
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse4.message());
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse5.message());
        // data is not null
        assertTrue(apiResponse1.data().size() > 0);
        assertTrue(apiResponse2.data().size() > 0);
        assertTrue(apiResponse3.data().size() > 0);
        assertTrue(apiResponse4.data().size() > 0);
        assertTrue(apiResponse5.data().size() > 0);
    }
} 
