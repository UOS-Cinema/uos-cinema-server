package com.uos.dsd.cinema.acceptance.hello;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayNameGeneration;
import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.hello.steps.HelloSteps;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

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
}
