package com.uos.dsd.cinema.acceptance.theater.steps;

import static io.restassured.RestAssured.given;
import io.restassured.response.Response;

public class TheaterSteps {

    private static final String BASE_PATH = "/theaters";
    private static final String DETAIL_PATH = BASE_PATH + "/{theaterNumber}";

    public static Response getAllTheaters() {

        return given().log().all()
            .when()
                .get(BASE_PATH)
            .then().log().all()
                .extract()
            .response();
    }

    public static Response getTheater(Long theaterNumber) {

        return given().log().all()
                .pathParam("theaterNumber", theaterNumber)
            .when()
                .get(DETAIL_PATH)
            .then().log().all()
                .extract()
            .response();
    }
}
