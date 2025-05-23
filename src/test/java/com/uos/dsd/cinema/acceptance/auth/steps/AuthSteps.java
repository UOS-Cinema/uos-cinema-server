package com.uos.dsd.cinema.acceptance.auth.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

import java.util.Map;

public class AuthSteps {

    private static final String LOGOUT_URL = "/logout";
    private static final String REFRESH_TOKEN_URL = "/refresh-token";

    public static Response sendLogout(Map<String, Object> headers, Map<String, Object> cookies) {

        return given().log().all()
                    .headers(headers)
                    .cookies(cookies)
                .when()
                    .post(LOGOUT_URL)
                .then().log().all()
                    .extract().response();
    }

    public static Response sendRefreshToken(Map<String, Object> headers, Map<String, Object> cookies) {

        return given().log().all()
                    .headers(headers)
                    .cookies(cookies)
                .when()
                    .post(REFRESH_TOKEN_URL)
                .then().log().all()
                    .extract().response();
    }
}
