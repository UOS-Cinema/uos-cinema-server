package com.uos.dsd.cinema.acceptance.guest.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.guest.request.GuestLoginRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class GuestSteps {

    private static final String GUEST_LOGIN_URL = "/guests/login";
    private static final String GUEST_GET_INFO_URL = "/guests/{id}";

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

    public static Response sendGetGuestInfo(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .headers(headers)
                .when()
                .get(GUEST_GET_INFO_URL, id)
            .then().log().all()
                .extract()
                .response();
    }
} 
