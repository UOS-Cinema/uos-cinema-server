package com.uos.dsd.cinema.acceptance.genre;

import static io.restassured.RestAssured.given;

import io.restassured.response.Response;

public class GenreSteps {

    private static final String BASE_URI = "/genres";

    public static Response getAllGenre() {
        return given()
            .when()
                .get(BASE_URI)
            .then().log().all()
                .extract()
            .response();
    }
}
