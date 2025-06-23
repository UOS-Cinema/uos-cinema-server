package com.uos.dsd.cinema.acceptance.customer.guest.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.customer.guest.request.GuestLoginRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class GuestSteps {

    private static final String GUEST_LOGIN_URL = "/guests/login";
    private static final String GUEST_GET_INFO_URL = "/guests/info";

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
            Map<String, Object> headers) {

        return given().log().all()
                .headers(headers)
                .when()
                .get(GUEST_GET_INFO_URL)
            .then().log().all()
                .extract()
                .response();
    }
} 
