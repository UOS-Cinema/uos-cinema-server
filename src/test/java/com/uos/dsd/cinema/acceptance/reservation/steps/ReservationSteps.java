package com.uos.dsd.cinema.acceptance.reservation.steps;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import com.uos.dsd.cinema.adaptor.in.web.reservation.request.ReserveRequest;

public class ReservationSteps {

    private static final String BASE_URL = "/reservations";
    private static final String DETAIL_URL = BASE_URL + "/{id}";

    public static Response reserve(Map<String, Object> headers, ReserveRequest request) {
        return given().log().all()
                .headers(headers)
                .body(request)
                .contentType(ContentType.JSON)
            .when().log().all()
                .post(BASE_URL)
            .then().log().all()
                .extract()
            .response();
    }
    
    public static Response cancel(Map<String, Object> headers, Long id) {
        return given().log().all()
                .headers(headers)
                .pathParam("id", id)
            .when().log().all()
                .delete(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }
}
