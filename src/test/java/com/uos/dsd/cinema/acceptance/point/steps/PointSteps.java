package com.uos.dsd.cinema.acceptance.point.steps;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.Map;

public class PointSteps {

    public static Response sendGetPoint(Map<String, Object> headers) {
        return RestAssured.given()
                .headers(headers)
                .when()
                .get("/members/points")
                .then()
                .extract()
                .response();
    }

    public static Response sendGetPointHistory(Map<String, Object> headers, int page, int size) {
        return RestAssured.given()
                .headers(headers)
                .queryParam("page", page)
                .queryParam("size", size)
                .when()
                .get("/members/points/history")
                .then()
                .extract()
                .response();
    }
} 
