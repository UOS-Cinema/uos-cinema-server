package com.uos.dsd.cinema.acceptance.guest.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.guest.request.GuestLoginRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class GuestSteps {

    private static final String GUEST_LOGIN_URL = "/guests/login";

    public static Response sendLoginGuest(
            Map<String, Object> headers,
            GuestLoginRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(GUEST_LOGIN_URL)
            .then().log().all()
                .extract()
                .response();
    }
} 
