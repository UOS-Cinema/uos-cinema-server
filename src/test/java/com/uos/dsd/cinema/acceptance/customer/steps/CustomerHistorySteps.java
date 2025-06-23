package com.uos.dsd.cinema.acceptance.customer.steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class CustomerHistorySteps {

    public static Response sendGetReservationHistory(Map<String, Object> headers, int page, int size) {
        return RestAssured.given()
                .headers(headers)
                .queryParam("page", page)
                .queryParam("size", size)
                .when()
                .get("/customers/reservations")
                .then()
                .extract()
                .response();
    }

    public static Response sendGetPaymentHistory(Map<String, Object> headers, int page, int size) {
        return RestAssured.given()
                .headers(headers)
                .queryParam("page", page)
                .queryParam("size", size)
                .when()
                .get("/customers/payments")
                .then()
                .extract()
                .response();
    }
} 
