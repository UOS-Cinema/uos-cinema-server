package com.uos.dsd.cinema.acceptance.movie;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.acceptance.movie.steps.MovieSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieListRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieDetailResponse;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieListResponse;
import com.uos.dsd.cinema.adaptor.in.web.movie.response.MovieSimpleResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class MovieAcceptanceTest extends AcceptanceTest {

    private static final String EXIST_ADMIN_USERNAME = "administrator";
    private static final String EXIST_ADMIN_PASSWORD = "password123!";

    private static final Long TEST_MOVIE_ID = 1L;
    private static final Long NON_EXIST_MOVIE_ID = 999L;

    @Test
    public void createMovie() {
        /* Given */
        MovieCreateRequest request = createValidMovieCreateRequest();

        /* When */
        String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = MovieSteps.sendCreateMovie(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(1L, apiResponse.data());
    }

    @Test
    public void createMovieWithoutAdminToken() {
        /* Given */
        MovieCreateRequest request = createValidMovieCreateRequest();

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendCreateMovie(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void createMovieWithInvalidToken() {
        /* Given */
        MovieCreateRequest request = createValidMovieCreateRequest();

        /* When */
        String invalidToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

        Response response = MovieSteps.sendCreateMovie(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void updateMovie() {
        /* Given */
        MovieUpdateRequest request = createValidMovieUpdateRequest();

        /* When */
        String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = MovieSteps.sendUpdateMovie(headers, TEST_MOVIE_ID, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(TEST_MOVIE_ID, apiResponse.data());
    }

    @Test
    public void updateMovieWithoutAdminToken() {
        /* Given */
        MovieUpdateRequest request = createValidMovieUpdateRequest();

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendUpdateMovie(headers, TEST_MOVIE_ID, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void updateMovieWithInvalidToken() {
        /* Given */
        MovieUpdateRequest request = createValidMovieUpdateRequest();

        /* When */
        String invalidToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

        Response response = MovieSteps.sendUpdateMovie(headers, TEST_MOVIE_ID, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void deleteMovie() {
        /* Given */
        Long movieId = TEST_MOVIE_ID;

        /* When */
        String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = MovieSteps.sendDeleteMovie(headers, movieId);
        log.info("response: {}", response.asString());
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
    }

    @Test
    public void deleteMovieWithoutAdminToken() {
        /* Given */
        Long movieId = TEST_MOVIE_ID;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendDeleteMovie(headers, movieId);
        log.info("response: {}", response.asString());
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void deleteMovieWithInvalidToken() {
        /* Given */
        Long movieId = TEST_MOVIE_ID;

        /* When */
        String invalidToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

        Response response = MovieSteps.sendDeleteMovie(headers, movieId);
        log.info("response: {}", response.asString());
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void getMovieDetail() {
        /* Given */
        Long movieId = TEST_MOVIE_ID;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendGetMovieDetail(headers, movieId);
        log.info("response: {}", response.asString());
        ApiResponse<MovieDetailResponse> apiResponse = response.as(new TypeRef<ApiResponse<MovieDetailResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        MovieDetailResponse movieDetail = apiResponse.data();
        assertNotNull(movieDetail);
        assertEquals(movieId, movieDetail.id());
        assertEquals("Mock Title", movieDetail.title());
        assertEquals("Mock Synopsis", movieDetail.synopsis());
        assertEquals(120L, movieDetail.runningTime());
        assertEquals(MovieRating.FIFTEEN, movieDetail.rating());
        assertEquals("poster.jpg", movieDetail.posterUrls());
        assertEquals(LocalDate.now(), movieDetail.releaseDate());
        assertEquals("Mock Distributor", movieDetail.distributorName());
        assertNotNull(movieDetail.director());
        assertNotNull(movieDetail.actors());
        assertNotNull(movieDetail.genres());
    }

    @Test
    public void getMovieSimple() {
        /* Given */
        Long movieId = TEST_MOVIE_ID;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendGetMovieSimple(headers, movieId);
        log.info("response: {}", response.asString());
        ApiResponse<MovieSimpleResponse> apiResponse = response.as(new TypeRef<ApiResponse<MovieSimpleResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        MovieSimpleResponse movieSimple = apiResponse.data();
        assertNotNull(movieSimple);
        assertEquals(movieId, movieSimple.id());
        assertEquals("Mock Title", movieSimple.title());
        assertEquals("poster.jpg", movieSimple.posterUrls());
        assertEquals(LocalDate.now(), movieSimple.releaseDate());
    }

    @Test
    public void searchMovies() {
        /* Given */
        MovieSearchRequest request = new MovieSearchRequest(
            "test", null, null, null, null, null, 0, 10
        );

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendSearchMovies(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<MovieListResponse> apiResponse = response.as(new TypeRef<ApiResponse<MovieListResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        MovieListResponse movieList = apiResponse.data();
        assertNotNull(movieList);
        assertEquals(List.of(1L, 2L, 3L), movieList.movieIds());
        assertEquals(3, movieList.totalCount());
        assertEquals(0, movieList.currentPage());
        assertEquals(1, movieList.totalPages());
    }

    @Test
    public void getNowPlayingMovies() {
        /* Given */
        MovieListRequest request = new MovieListRequest(0, 10);

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendGetNowPlayingMovies(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<MovieListResponse> apiResponse = response.as(new TypeRef<ApiResponse<MovieListResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        MovieListResponse movieList = apiResponse.data();
        assertNotNull(movieList);
        assertEquals(List.of(1L, 2L, 3L), movieList.movieIds());
        assertEquals(3, movieList.totalCount());
        assertEquals(0, movieList.currentPage());
        assertEquals(1, movieList.totalPages());
    }

    @Test
    public void getUpcomingMovies() {
        /* Given */
        MovieListRequest request = new MovieListRequest(0, 10);

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = MovieSteps.sendGetUpcomingMovies(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<MovieListResponse> apiResponse = response.as(new TypeRef<ApiResponse<MovieListResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        MovieListResponse movieList = apiResponse.data();
        assertNotNull(movieList);
        assertEquals(List.of(4L, 5L, 6L), movieList.movieIds());
        assertEquals(3, movieList.totalCount());
        assertEquals(0, movieList.currentPage());
        assertEquals(1, movieList.totalPages());
    }

    private MovieCreateRequest createValidMovieCreateRequest() {
        return new MovieCreateRequest(
            "Test Movie",
            "Test Synopsis",
            120L,
            MovieRating.FIFTEEN,
            "test-poster.jpg",
            LocalDate.now(),
            "Test Distributor",
            1L,
            List.of("DOLBY"),
            List.of(new MovieCreateRequest.ActorCastingRequest(1L, "주인공", CastingType.LEAD)),
            List.of("액션", "드라마")
        );
    }

    private MovieUpdateRequest createValidMovieUpdateRequest() {
        return new MovieUpdateRequest(
            "Updated Movie",
            "Updated Synopsis",
            130L,
            MovieRating.TWELVE,
            "updated-poster.jpg",
            LocalDate.now().plusDays(30),
            "Updated Distributor",
            2L,
            List.of("IMAX"),
            List.of(new MovieUpdateRequest.ActorCastingRequest(2L, "조연", CastingType.SUPPORTING)),
            List.of("스릴러", "공포")
        );
    }

    void checkSuccess(int statusCode, String code) {
        assertEquals(200, statusCode);
        assertEquals(CommonResultCode.SUCCESS.getCode(), code);
    }

    void checkUnauthorized(int statusCode, String code) {
        assertEquals(401, statusCode);
        assertEquals(CommonResultCode.UNAUTHORIZED.getCode(), code);
    }

    /**
     * Login and return accessToken
     */
    private String getAccessToken(String username, String password) {
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);
        return apiResponse.data().accessToken();
    }
} 
