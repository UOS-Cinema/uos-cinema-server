package com.uos.dsd.cinema.acceptance.movie.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.application.port.in.movie.query.MovieQueryCondition;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class MovieSteps {

    private static final String BASE_URL = "/movies";
    private static final String DETAIL_URL = BASE_URL + "/{id}";

    public static Response sendGetMovie(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .headers(headers)
                .pathParam("id", id)
            .when()
                .get(DETAIL_URL)
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendSearchMovies(
            Map<String, Object> headers,
            MovieQueryCondition request) {

        Map<String, Object> queryParams = new HashMap<>();
        if (request.title() != null)
            queryParams.put("title", request.title());
        if (request.startDate() != null)
            queryParams.put("startDate", request.startDate().toString());
        if (request.endDate() != null)
            queryParams.put("endDate", request.endDate().toString());
        if (request.genres() != null)
            queryParams.put("genres", request.genres());
        if (request.screenTypes() != null)
            queryParams.put("screenTypes", request.screenTypes());
        if (request.sortBy() != null)
            queryParams.put("sortBy", request.sortBy());
        if (request.page() != null)
            queryParams.put("page", request.page());
        if (request.size() != null)
            queryParams.put("size", request.size());

        return given().log().all().headers(headers).queryParams(queryParams).when().get(BASE_URL)
                .then().log().all().extract().response();
    }

    public static Response sendGetRankingMovies(
            Map<String, Object> headers,
            Integer page,
            Integer size) {

        return given().log().all()
                .headers(headers)
                .queryParam("page", page)
                .queryParam("size", size)
            .when()
                .get(BASE_URL + "/ranking")
            .then().log().all()
                .extract()
            .response();
    }

    public static Response sendGetUpcomingMovies(
            Map<String, Object> headers,
            Integer page,
            Integer size) {

        return given().log().all()
                .headers(headers)
                .queryParam("page", page)
                .queryParam("size", size)
            .when()
                .get(BASE_URL + "/upcoming")
            .then().log().all()
                .extract()
            .response();
    }
} 
