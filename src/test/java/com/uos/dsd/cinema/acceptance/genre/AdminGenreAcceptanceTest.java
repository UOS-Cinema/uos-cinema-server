package com.uos.dsd.cinema.acceptance.genre;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.adapter.in.genre.GenreUIFIxture;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;
import com.uos.dsd.cinema.adaptor.in.web.genre.response.GenreResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminGenreAcceptanceTest extends AcceptanceTest {

    private final String adminAccessToken = loginAdmin();
    
    @Test
    public void createAndGetGenreTest() {
        /* when */
        // 1. Create genre
        Response response = AdminGenreSteps.createGenre(
                AuthHeaderProvider.createAuthorizationHeader(adminAccessToken),
                GenreUIFIxture.createGenreRequest);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        String genreName = apiResponse.data();
        log.info("apiResponse: {}", apiResponse);

        // 2. Get genre
        List<GenreResponse> genreResponses = getGenreResponses(genreName);
        log.info("genreResponses: {}", genreResponses);

        /* then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data contains genre name
        assertTrue(genreResponses.stream()
        .anyMatch(genre -> genre.name().equals(genreName)));
    }

    @Test
    public void updateAndGetGenreTest() {
        /* when */
        // 1. Modify genre
        String genre = "Thriller";
        Response response = AdminGenreSteps.updateGenre(
                AuthHeaderProvider.createAuthorizationHeader(adminAccessToken),
                genre,
                GenreUIFIxture.updateGenreRequest);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        String genreName = apiResponse.data();
        log.info("apiResponse: {}", apiResponse);

        // 2. Get genre
        List<GenreResponse> genreResponses = getGenreResponses(genreName);
        log.info("genreResponses: {}", genreResponses);

        /* then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data contains genre name
        assertEquals(genreResponses.stream().filter(
                    g -> g.name().equals(genreName))
                    .findFirst().get().description(), 
                GenreUIFIxture.updateGenreRequest.description());
    }

    @Test
    public void deleteGenreTest() {
        /* when */
        // 1. Delete genre
        String genre = "Musical";
        Response response = AdminGenreSteps
                .deleteGenre(AuthHeaderProvider.createAuthorizationHeader(adminAccessToken), genre);
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("apiResponse: {}", apiResponse);

        // 2. Get genre
        List<GenreResponse> genreResponses =
                getGenreResponses(GenreUIFIxture.createGenreRequest.name());
        log.info("genreResponses: {}", genreResponses);

        /* then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data contains genre name
        assertTrue(genreResponses.stream().noneMatch(g -> g.name().equals(genre)));
    }

    @Test
    public void deleteGenreFailedTest() {
        /* when */
        // 1. Delete genre
        String genre = "Thriller";
        Response response = AdminGenreSteps.deleteGenre(
                AuthHeaderProvider.createAuthorizationHeader(adminAccessToken),
                genre);
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("apiResponse: {}", apiResponse);

        // 2. Get genre
        List<GenreResponse> genreResponses = getGenreResponses(GenreUIFIxture.createGenreRequest.name());
        log.info("genreResponses: {}", genreResponses);

        /* then */
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: COM001
        assertEquals(CommonResultCode.BAD_REQUEST.getCode(), apiResponse.code());
        // data contains genre name
        assertTrue(genreResponses.stream()
                .anyMatch(g -> g.name().equals(genre)));
    }

    private List<GenreResponse> getGenreResponses(String genreName) {
        Response genreResponse = GenreSteps.getAllGenre();
        ApiResponse<List<GenreResponse>> genreApiResponse =
                genreResponse.as(
            new TypeRef<ApiResponse<List<GenreResponse>>>() {});
        return genreApiResponse.data();
    }
}
