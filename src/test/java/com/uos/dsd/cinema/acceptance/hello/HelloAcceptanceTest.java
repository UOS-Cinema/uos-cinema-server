package com.uos.dsd.cinema.acceptance.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayNameGeneration;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
import com.uos.dsd.cinema.acceptance.hello.steps.HelloSteps;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

import java.util.Map;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class HelloAcceptanceTest extends AcceptanceTest {

    @Test
    public void hello() {

        Response response = HelloSteps.getHello();
        log.info("response: {}", response.asString());
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});

        // status code: 200
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data: Hello, UOS Cinema!
        assertEquals("Hello, UOS Cinema!", apiResponse.data());
    }

    @Test
    public void helloAuth() {

        // given
        Long userId = -1L;
        String username = "administrator";
        String password = "password123!";
        AdminLoginRequest request = new AdminLoginRequest(username, password);

        Response loginResponse = AdminSteps.sendLoginAdmin(AuthHeaderProvider.createEmptyHeader(), request);
        String accessToken = loginResponse.jsonPath().getString("data.accessToken");
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        // when
        Response response = HelloSteps.getHelloAuth(headers);
        log.info("response: {}", response.asString());

        // then
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        assertEquals(200, response.statusCode());
        // code: COM000
        assertEquals(CommonResultCode.SUCCESS.getCode(), apiResponse.code());
        // message: Success
        assertEquals(CommonResultCode.SUCCESS.getMessage(), apiResponse.message());
        // data: Hello, UOS Cinema!
        assertEquals("Hello, UOS Cinema! " + userId + " ADMIN", apiResponse.data());
    }
}
