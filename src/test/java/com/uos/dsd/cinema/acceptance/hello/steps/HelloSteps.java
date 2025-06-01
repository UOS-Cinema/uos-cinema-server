package com.uos.dsd.cinema.acceptance.hello.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

import java.util.Map;

public class HelloSteps {

    public static Response getHello() {

        return given().log().all()
            .when()
                .get("/hello")
            .then().log().all()
                .extract()
            .response();
    }

    public static Response getHelloAuth(Map<String, Object> headers) {

        return given().log().all()
                .headers(headers)
            .when()
                .get("/hello/auth")
            .then().log().all()
                .extract()
            .response();
    }
}
