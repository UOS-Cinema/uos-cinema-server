package com.uos.dsd.cinema.acceptance.screening.steps;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningUpdateRequest;

public class ScreeningAdminSteps {

    private static final String BASE_PATH = "/admin/screenings";
    private static final String DETAIL_PATH = BASE_PATH + "/{id}";

    public static Response postScreening(
    Map<String, Object> headers,
    ScreeningCreateRequest request) {

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

    public static Response putScreening(
        Map<String, Object> headers,
        Long id,
        ScreeningUpdateRequest request) {

        return given().log().all()
                .headers(headers)
                .pathParam("id", id)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .put(DETAIL_PATH)
            .then().log().all()
                .extract()
            .response();
    }
    
    public static Response deleteScreening(
        Map<String, Object> headers,
        Long id) {

        return given().log().all()
                .headers(headers)
                .pathParam("id", id)
            .when()
                .delete(DETAIL_PATH)
            .then().log().all()
                .extract()
            .response();
    }
}
