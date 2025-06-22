package com.uos.dsd.cinema.acceptance.theater.steps;

import static io.restassured.RestAssured.given;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class AdminTheaterSteps {

    private static final String BASE_PATH = "/admin/theaters";
    private static final String DETAIL_PATH = BASE_PATH + "/{theaterNumber}";

    public static Response postTheater(
        Map<String, Object> headers,
        TheaterCreateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(BASE_PATH)
            .then().log().all()
                .extract()
            .response();
    }

    public static Response updateTheater(
        Long theaterNumber,
        Map<String, Object> headers,
        TheaterUpdateRequest request) {

        return given().log().all()
                .headers(headers)
                .pathParam("theaterNumber", theaterNumber)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .put(DETAIL_PATH)
            .then().log().all()
                .extract()
            .response();
    }

    public static Response deleteTheater(
        Long theaterNumber,
        Map<String, Object> headers) {

        return given().log().all()
                .headers(headers)
                .pathParam("theaterNumber", theaterNumber)
            .when()
                .delete(DETAIL_PATH)
            .then().log().all()
                .extract()
            .response();
    }
}
