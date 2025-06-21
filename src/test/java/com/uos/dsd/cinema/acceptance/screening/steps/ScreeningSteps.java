package com.uos.dsd.cinema.acceptance.screening.steps;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.response.Response;

public class ScreeningSteps {

    private static final String BASE_PATH = "/screenings";
    private static final String DETAIL_PATH = BASE_PATH + "/{id}";

    public static Response getScreening(
        Map<String, Object> headers,
        Long id) {

        if (id == null) {
            throw new IllegalArgumentException("Screening ID cannot be null");
        }

        return given().log().all()
                .headers(headers)   
                .pathParam("id", id)
            .when()
                .get(DETAIL_PATH)
            .then().log().all()
                .extract()
            .response();
    }

    public static Response getScreenings(
        Map<String, Object> headers,
        Long movieId,
        Long theaterId,
        String date) {

        return given().log().all()
                .headers(headers)
                .queryParam("movieId", movieId)
                .queryParam("theaterId", theaterId)
                .queryParam("date", date)
            .when()
                .get(BASE_PATH)
            .then().log().all()
                .extract()
            .response();
    }
}
