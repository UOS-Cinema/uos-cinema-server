package com.uos.dsd.cinema.acceptance.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.*;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.*;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.exception.IllegalPasswordException;
import com.uos.dsd.cinema.domain.exception.IllegalUsernameException;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.Map;
import java.util.stream.Stream;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminAcceptanceTest extends AcceptanceTest {

    private static final Long EXIST_ADMIN_ID = 1L;
    private static final String EXIST_ADMIN_USERNAME = "administrator";
    private static final String EXIST_ADMIN_PASSWORD = "password123!";
    private static final String WRONG_ADMIN_PASSWORD = "wrongpw123!";

    private static final Long DELETED_ADMIN_ID = 2L;
    private static final String DELETED_ADMIN_USERNAME = "deletedAdministrator";
    private static final String DELETED_ADMIN_PASSWORD = "password123!";

    private static final Long NOT_EXIST_ADMIN_ID = 999L;
    private static final String NOT_EXIST_ADMIN_USERNAME = "notexistAdmin";
    private static final String NOT_EXIST_ADMIN_PASSWORD = "notexistpw123!";

    private static final String NEW_ADMIN_USERNAME = "newAdministrator";
    private static final String NEW_ADMIN_PASSWORD = "newpassword123!";

    private final JwtUtils jwtUtils;

    @Autowired
    public AdminAcceptanceTest(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Test
    public void signup() {

        /* Given */
        String username = NEW_ADMIN_USERNAME;
        String password = NEW_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = jwtUtils.generateAccessToken(1L, Role.ADMIN);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(username, apiResponse.data().username());
    }

    @Test
    public void signupWithInvalidToken() {

        /* Given */
        String username = NEW_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 401
        assertEquals(401, response.statusCode());
        // code: COM003
        assertEquals("COM003", apiResponse.code());
        // message: Full authentication is required to access this resource
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
    }

    @ParameterizedTest
    @ValueSource(strings = { EXIST_ADMIN_USERNAME, DELETED_ADMIN_USERNAME })
    public void signupWithExistingName(String username) {

        /* Given */
        String password = NEW_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = jwtUtils.generateAccessToken(1L, Role.ADMIN);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);
        
        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals("Admin username already exists", apiResponse.message());
    }

    @ParameterizedTest
    @ValueSource(strings = { "short", "longlonglonglonglong1", "한글", "space space" })
    public void signupWithInvalidUsername(String username) {

        /* Given */
        String password = NEW_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = jwtUtils.generateAccessToken(1L, Role.ADMIN);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalUsernameException.MESSAGE, apiResponse.message());
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345678", "abcdefgh", "!@#$%^&*()_+", "nospecials123", "!@!@1234", "nodigits!!!",
            "short!1", "한글", "space space" })
    public void signupWithInvalidPassword(String password) {

        /* Given */
        String username = NEW_ADMIN_USERNAME;

        /* When */
        String adminAccessToken = jwtUtils.generateAccessToken(1L, Role.ADMIN);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalPasswordException.MESSAGE, apiResponse.message());
    }

    @Test
    public void login() {

        /* Given */
        String username = EXIST_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        // check body accessToken
        String accessToken = apiResponse.data().accessToken();
        String accessTokenUsername = jwtUtils.getUsernameFromJwtToken(accessToken);
        assertEquals(EXIST_ADMIN_USERNAME, accessTokenUsername);
        // check cookie refreshToken
        String refreshToken = response.getCookie(SecurityConstants.REISSUE_COOKIE_NAME);
        String refreshTokenUsername = jwtUtils.getUsernameFromJwtToken(refreshToken);
        assertEquals(EXIST_ADMIN_USERNAME, refreshTokenUsername);
    }

    @ParameterizedTest
    @CsvSource({
        EXIST_ADMIN_USERNAME + "," + WRONG_ADMIN_PASSWORD,
        DELETED_ADMIN_USERNAME + "," + DELETED_ADMIN_PASSWORD,
        NOT_EXIST_ADMIN_USERNAME + "," + NOT_EXIST_ADMIN_PASSWORD
    })
    public void loginFailure(String username, String password) {
        /* Given */
        // username and password are provided as parameters

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Invalid admin username or password", apiResponse.message());
    }

    @Test
    public void updatePassword() {
        /* Given */
        Long id = EXIST_ADMIN_ID;
        String currentPassword = EXIST_ADMIN_PASSWORD;
        String newPassword = NEW_ADMIN_PASSWORD;

        /* When */
        // TODO: Admin 권한의 Access Token이 주어져야 함
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendUpdateAdmin(headers, new AdminUpdateRequest(id, currentPassword, newPassword));
        log.info("response: {}", response.asString());
        ApiResponse<AdminUpdateResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminUpdateResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(id, apiResponse.data().id());

        /* Login Test */
        Map<String, Object> loginHeaders = AuthHeaderProvider.createEmptyHeader();
        Response loginResponse = AdminSteps.sendLoginAdmin(loginHeaders, new AdminLoginRequest(EXIST_ADMIN_USERNAME, newPassword)); 
        ApiResponse<AdminLoginResponse> loginApiResponse = loginResponse.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});

        checkSuccess(loginResponse.statusCode(), loginApiResponse.code());
        // check body accessToken
        String accessToken = loginApiResponse.data().accessToken();
        String accessTokenUsername = jwtUtils.getUsernameFromJwtToken(accessToken);
        assertEquals(EXIST_ADMIN_USERNAME, accessTokenUsername);
        // check cookie refreshToken
        String refreshToken = loginResponse.getCookie("refreshToken");
        String refreshTokenUsername = jwtUtils.getUsernameFromJwtToken(refreshToken);
        assertEquals(EXIST_ADMIN_USERNAME, refreshTokenUsername);
    }

    /*
     * provide id, password
     */
    private static Stream<Arguments> provideInvalidAdminCredentials() {

        return Stream.of(
            Arguments.of(EXIST_ADMIN_ID, WRONG_ADMIN_PASSWORD),
            Arguments.of(DELETED_ADMIN_ID, DELETED_ADMIN_PASSWORD),
            Arguments.of(NOT_EXIST_ADMIN_ID, NOT_EXIST_ADMIN_PASSWORD)
        );
    }
    
    @ParameterizedTest
    @MethodSource("provideInvalidAdminCredentials")
    public void updatePasswordFailure(Long id, String currentPassword) {
        /* Given */
        String newPassword = NEW_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendUpdateAdmin(headers, new AdminUpdateRequest(id, currentPassword, newPassword));
        log.info("response: {}", response.asString());
        ApiResponse<AdminUpdateResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminUpdateResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Invalid admin current password", apiResponse.message());
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345678", "abcdefgh", "!@#$%^&*()_+", "nospecials123", "!@!@1234", "nodigits!!!",
            "short!1", "한글", "space space" })
    public void updatePasswordWithInvalidNewPassword(String newPassword) {
        /* Given */
        Long id = EXIST_ADMIN_ID;
        String currentPassword = EXIST_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        log.info("adminid: {}, currentPassword: {}, newPassword: {}", id, currentPassword, newPassword);

        Response response = AdminSteps.sendUpdateAdmin(headers, new AdminUpdateRequest(id, currentPassword, newPassword));
        log.info("response: {}", response.asString());
        ApiResponse<AdminUpdateResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminUpdateResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalPasswordException.MESSAGE, apiResponse.message());
    }

    @Test
    public void deleteAdmin() {
        /* Given */
        Long id = EXIST_ADMIN_ID;
        String username = EXIST_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = AdminSteps.sendDeleteAdmin(headers, new AdminDeleteRequest(id, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminDeleteResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminDeleteResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(id, apiResponse.data().id());

        /* Login Test -> fail */
        Map<String, Object> loginHeaders = AuthHeaderProvider.createEmptyHeader();
        Response loginResponse = AdminSteps.sendLoginAdmin(loginHeaders, new AdminLoginRequest(username, password)); 
        ApiResponse<AdminLoginResponse> loginApiResponse = loginResponse.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});

        checkUnauthorized(loginResponse.statusCode(), loginApiResponse.code());
        assertEquals("Invalid admin username or password", loginApiResponse.message());

        /* Update Test -> fail */
        Response updateResponse = AdminSteps.sendUpdateAdmin(headers, new AdminUpdateRequest(id, password, NEW_ADMIN_PASSWORD));
        ApiResponse<AdminUpdateResponse> updateApiResponse = updateResponse.as(new TypeRef<ApiResponse<AdminUpdateResponse>>() {});

        checkUnauthorized(updateResponse.statusCode(), updateApiResponse.code());
        assertEquals("Invalid admin current password", updateApiResponse.message());

        /* Delete Test -> fail */
        Response deleteResponse = AdminSteps.sendDeleteAdmin(headers, new AdminDeleteRequest(id, password));
        ApiResponse<AdminDeleteResponse> deleteApiResponse = deleteResponse.as(new TypeRef<ApiResponse<AdminDeleteResponse>>() {});
        
        checkUnauthorized(deleteResponse.statusCode(), deleteApiResponse.code());
        assertEquals("Invalid admin id or password", deleteApiResponse.message());

        /* Signup Test -> fail */
        Response signupResponse = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        ApiResponse<AdminSignupResponse> signupApiResponse = signupResponse.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});

        checkBadRequest(signupResponse.statusCode(), signupApiResponse.code());
        assertEquals("Admin username already exists", signupApiResponse.message());
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAdminCredentials")
    void deleteAdminFailure(Long id, String password) {
        /* Given */
        // id and password are provided as parameters

        /* When */
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendDeleteAdmin(headers, new AdminDeleteRequest(id, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminDeleteResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminDeleteResponse>>() {});

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Invalid admin id or password", apiResponse.message());
    }

    void checkSuccess(int statusCode, String code) {
        assertEquals(200, statusCode);
        assertEquals(CommonResultCode.SUCCESS.getCode(), code);
    }

    void checkBadRequest(int statusCode, String code) {
        assertEquals(400, statusCode);
        assertEquals(CommonResultCode.BAD_REQUEST.getCode(), code);
    }

    void checkUnauthorized(int statusCode, String code) {
        assertEquals(401, statusCode);
        assertEquals(CommonResultCode.UNAUTHORIZED.getCode(), code);
    }
}
