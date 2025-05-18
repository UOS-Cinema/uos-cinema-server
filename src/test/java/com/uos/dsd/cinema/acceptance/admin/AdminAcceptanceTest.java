package com.uos.dsd.cinema.acceptance.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminSignupResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminAcceptanceTest extends AcceptanceTest {

    private static final String NEW_ADMIN_USERNAME = "newAdministrator";
    private static final String EXIST_ADMIN_USERNAME = "administrator";
    private static final String EXIST_ADMIN_PASSWORD = "password123!";
    private static final String INVALID_ADMIN_PASSWORD = "invalidpw123!";

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
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals("COM000", apiResponse.code());
        // body username: NEW_ADMIN_USERNAME
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
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: COM001
        assertEquals("COM001", apiResponse.code());
        // message: Admin name already exists
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
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: COM001
        assertEquals("COM001", apiResponse.code());
        // message: Invalid name format
        assertEquals("아이디는 6자 이상 20자 이하이며, 영문자와 숫자만 포함해야 합니다.", apiResponse.message());
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
        // status code: 400
        assertEquals(400, response.statusCode());
        // code: COM001
        assertEquals("COM001", apiResponse.code());
        // message: Invalid password format
        assertEquals("비밀번호는 8자 이상 20자 이하이며, 영문자, 숫자, 특수문자를 포함해야 합니다.", apiResponse.message());
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
        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals("COM000", apiResponse.code());
        // TODO: check body accessToken, refreshToken
    }

    @Test
    public void loginWithNotExistedAdmin() {

        /* Given */
        String username = NEW_ADMIN_USERNAME;
        String password = EXIST_ADMIN_PASSWORD;

        /* When */
        Map<String, Object> headers = new HashMap<>();

        Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 401
        assertEquals(401, response.statusCode());
        // code: COM003
        assertEquals("COM003", apiResponse.code());
        // message: Invalid admin name or password
        assertEquals("Invalid admin username or password", apiResponse.message());
    }

    @Test
    public void loginWithInvalidPassword() {
        /* Given */
        String username = EXIST_ADMIN_USERNAME;
        String password = INVALID_ADMIN_PASSWORD;

        /* When */
        Map<String, Object> headers = new HashMap<>();

        Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        // status code: 401
        assertEquals(401, response.statusCode());
        // code: COM003
        assertEquals("COM003", apiResponse.code());
        // message: Invalid admin name or password
        assertEquals("Invalid admin username or password", apiResponse.message());
    }
}
