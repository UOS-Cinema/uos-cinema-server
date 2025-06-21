package com.uos.dsd.cinema.acceptance.screening.steps;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.response.Response;

public class ScreeningReservationSteps {

    private static final String BASE_PATH = "/screenings/{id}/reservations";

    public static Response getScreeningReservationSeatingStatus(
        Map<String, Object> headers,
        Long id) {

        return given().log().all()
                .headers(headers)
                .pathParam("id", id)
            .when()
                .get(BASE_PATH)
            .then().log().all()
                .extract()
            .response();
    }
}
