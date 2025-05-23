package com.uos.dsd.cinema.acceptance.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminDeleteRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminDeleteResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminSignupResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminUpdateResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.exception.IllegalPasswordException;
import com.uos.dsd.cinema.domain.exception.IllegalUsernameException;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminAcceptanceTest extends AcceptanceTest {

    private static final String NEW_ADMIN_USERNAME = "newAdministrator";
    private static final String EXIST_ADMIN_USERNAME = "administrator";
    private static final String EXIST_ADMIN_PASSWORD = "password123!";
    private static final String INVALID_ADMIN_PASSWORD = "invalidpw123!";
    private static final String NEW_ADMIN_PASSWORD = "newpassword123!";
    private static final Long ADMIN_ID = 0L;
    private static final Long INVALID_ADMIN_ID = 999L;

    @Test
    public void signup() {

        /* Given */
        String username = NEW_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        // TODO: Admin 권한의 Access Token이 주어져야 함
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendSignupAdmin(headers, new AdminSignupRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(NEW_ADMIN_USERNAME, apiResponse.data().username());
    }

    @Test
    public void signupWithExistingName() {

        /* Given */
        String username = EXIST_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        // TODO: Admin 권한의 Access Token이 주어져야 함
        String adminAccessToken = "adminAccessToken";
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
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        // TODO: Admin 권한의 Access Token이 주어져야 함
        String adminAccessToken = "adminAccessToken";
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
        // TODO: Admin 권한의 Access Token이 주어져야 함
        String adminAccessToken = "adminAccessToken";
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
        Map<String, Object> headers = new HashMap<>();

        Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        // TODO: check body accessToken, refreshToken
    }

    @ParameterizedTest
    @CsvSource({
        NEW_ADMIN_USERNAME + "," + EXIST_ADMIN_PASSWORD,
        EXIST_ADMIN_USERNAME + "," + INVALID_ADMIN_PASSWORD
    })
    public void loginFailure(String username, String password) {
        /* Given */
        // username and password are provided as parameters

        /* When */
        Map<String, Object> headers = new HashMap<>();

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
        Long adminId = ADMIN_ID;
        String currentPassword = EXIST_ADMIN_PASSWORD;
        String newPassword = NEW_ADMIN_PASSWORD;

        /* When */
        // TODO: Admin 권한의 Access Token이 주어져야 함
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendUpdateAdmin(headers, new AdminUpdateRequest(adminId, currentPassword, newPassword));
        log.info("response: {}", response.asString());
        ApiResponse<AdminUpdateResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminUpdateResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(ADMIN_ID, apiResponse.data().id());

        /* Login Test */
        Map<String, Object> loginHeaders = AuthHeaderProvider.createEmptyHeader();
        Response loginResponse = AdminSteps.sendLoginAdmin(loginHeaders, new AdminLoginRequest(EXIST_ADMIN_USERNAME, newPassword)); 
        ApiResponse<AdminLoginResponse> loginApiResponse = loginResponse.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});

        checkSuccess(loginResponse.statusCode(), loginApiResponse.code());
        // TODO: check body accessToken, refreshToken
    }

    private static Stream<Arguments> provideInvalidAdminCredentials() {
        return Stream.of(
            Arguments.of(ADMIN_ID, INVALID_ADMIN_PASSWORD),
            Arguments.of(INVALID_ADMIN_ID, EXIST_ADMIN_PASSWORD)
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
        Long id = ADMIN_ID;
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
        Long id = ADMIN_ID;
        String username = EXIST_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        String adminAccessToken = "adminAccessToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

        Response response = AdminSteps.sendDeleteAdmin(headers, new AdminDeleteRequest(id, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminDeleteResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminDeleteResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(ADMIN_ID, apiResponse.data().id());

        /* Login Test -> fail */
        Map<String, Object> loginHeaders = AuthHeaderProvider.createEmptyHeader();
        Response loginResponse = AdminSteps.sendLoginAdmin(loginHeaders, new AdminLoginRequest(EXIST_ADMIN_USERNAME, password)); 
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
