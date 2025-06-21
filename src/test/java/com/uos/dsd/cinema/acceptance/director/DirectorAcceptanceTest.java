package com.uos.dsd.cinema.acceptance.director;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.acceptance.director.steps.DirectorSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorListRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.response.DirectorListResponse;
import com.uos.dsd.cinema.adaptor.in.web.director.response.DirectorMovieListResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.Map;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class DirectorAcceptanceTest extends AcceptanceTest {

    private static final String EXIST_ADMIN_USERNAME = "administrator";
    private static final String EXIST_ADMIN_PASSWORD = "password123!";

    private static final Long TEST_DIRECTOR_ID = 1L;
    private static final Long NON_EXIST_DIRECTOR_ID = 999L;

    @Test
    public void createDirector() {
        /* Given */
        DirectorCreateRequest request = createValidDirectorCreateRequest();

        /* When */
        String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = DirectorSteps.sendCreateDirector(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(1L, apiResponse.data());
    }

    @Test
    public void createDirectorWithoutAdminToken() {
        /* Given */
        DirectorCreateRequest request = createValidDirectorCreateRequest();

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = DirectorSteps.sendCreateDirector(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void createDirectorWithInvalidToken() {
        /* Given */
        DirectorCreateRequest request = createValidDirectorCreateRequest();

        /* When */
        String invalidToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

        Response response = DirectorSteps.sendCreateDirector(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void updateDirector() {
        /* Given */
        DirectorUpdateRequest request = createValidDirectorUpdateRequest();

        /* When */
        String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = DirectorSteps.sendUpdateDirector(headers, TEST_DIRECTOR_ID, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(TEST_DIRECTOR_ID, apiResponse.data());
    }

    @Test
    public void updateDirectorWithoutAdminToken() {
        /* Given */
        DirectorUpdateRequest request = createValidDirectorUpdateRequest();

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = DirectorSteps.sendUpdateDirector(headers, TEST_DIRECTOR_ID, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void updateDirectorWithInvalidToken() {
        /* Given */
        DirectorUpdateRequest request = createValidDirectorUpdateRequest();

        /* When */
        String invalidToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

        Response response = DirectorSteps.sendUpdateDirector(headers, TEST_DIRECTOR_ID, request);
        log.info("response: {}", response.asString());
        ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void deleteDirector() {
        /* Given */
        Long directorId = TEST_DIRECTOR_ID;

        /* When */
        String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = DirectorSteps.sendDeleteDirector(headers, directorId);
        log.info("response: {}", response.asString());
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
    }

    @Test
    public void deleteDirectorWithoutAdminToken() {
        /* Given */
        Long directorId = TEST_DIRECTOR_ID;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = DirectorSteps.sendDeleteDirector(headers, directorId);
        log.info("response: {}", response.asString());
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void deleteDirectorWithInvalidToken() {
        /* Given */
        Long directorId = TEST_DIRECTOR_ID;

        /* When */
        String invalidToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

        Response response = DirectorSteps.sendDeleteDirector(headers, directorId);
        log.info("response: {}", response.asString());
        ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @Test
    public void searchDirectors() {
        /* Given */
        DirectorSearchRequest request = new DirectorSearchRequest("test", 0, 10);

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = DirectorSteps.sendSearchDirectors(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<DirectorListResponse> apiResponse = response.as(new TypeRef<ApiResponse<DirectorListResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        DirectorListResponse directorList = apiResponse.data();
        assertNotNull(directorList);
        assertEquals(2, directorList.directors().size());
        assertEquals(2, directorList.totalCount());
        assertEquals(0, directorList.currentPage());
        assertEquals(1, directorList.totalPages());
    }

    @Test
    public void getDirectorList() {
        /* Given */
        DirectorListRequest request = new DirectorListRequest(0, 10);

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = DirectorSteps.sendGetDirectorList(headers, request);
        log.info("response: {}", response.asString());
        ApiResponse<DirectorListResponse> apiResponse = response.as(new TypeRef<ApiResponse<DirectorListResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        DirectorListResponse directorList = apiResponse.data();
        assertNotNull(directorList);
        assertEquals(3, directorList.directors().size());
        assertEquals(3, directorList.totalCount());
        assertEquals(0, directorList.currentPage());
        assertEquals(1, directorList.totalPages());
    }

    @Test
    public void getMoviesByDirector() {
        /* Given */
        Long directorId = TEST_DIRECTOR_ID;
        DirectorMovieSearchRequest request = new DirectorMovieSearchRequest(
            null, null, null, null, MovieSortType.POPULARITY, 0, 10
        );

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = DirectorSteps.sendGetMoviesByDirector(headers, directorId, request);
        log.info("response: {}", response.asString());
        ApiResponse<DirectorMovieListResponse> apiResponse = response.as(new TypeRef<ApiResponse<DirectorMovieListResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        DirectorMovieListResponse directorMovies = apiResponse.data();
        assertNotNull(directorMovies);
        assertEquals(3, directorMovies.movieIds().size());
        assertEquals(3, directorMovies.totalCount());
        assertEquals(0, directorMovies.currentPage());
        assertEquals(1, directorMovies.totalPages());
    }

    private DirectorCreateRequest createValidDirectorCreateRequest() {
        return new DirectorCreateRequest(
            "Test Director",
            "test-director.jpg"
        );
    }

    private DirectorUpdateRequest createValidDirectorUpdateRequest() {
        return new DirectorUpdateRequest(
            "Updated Director",
            "updated-director.jpg"
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
