package com.uos.dsd.cinema.acceptance.hello.steps;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class HelloSteps {

    public static Response getHello() {
        return given().log().all()
            .when()
                .get("/hello")
            .then().log().all()
                .extract()
            .response();
    }
}
