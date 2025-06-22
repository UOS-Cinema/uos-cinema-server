package com.uos.dsd.cinema.acceptance.movie.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class AdminMovieSteps {

    private static final String BASE_URL = "/admin/movies";
    private static final String DETAIL_URL = BASE_URL + "/{id}";

    public static Response sendCreateMovie(
            Map<String, Object> headers,
            MovieCreateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(BASE_URL)
            .then().log().all()
                .extract()
            .response();
    }

    public static Response sendUpdateMovie(
            Map<String, Object> headers,
            Long id,
            MovieUpdateRequest request) {

        return given().log().all()
                .pathParam("id", id)
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .put(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }

    public static Response sendDeleteMovie(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .pathParam("id", id)
                .headers(headers)
            .when()
                .delete(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }
}
