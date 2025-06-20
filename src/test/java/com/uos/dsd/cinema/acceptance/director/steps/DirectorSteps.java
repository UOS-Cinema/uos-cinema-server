package com.uos.dsd.cinema.acceptance.director.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorListRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorMovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorUpdateRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class DirectorSteps {

    private static final String DIRECTOR_CREATE_URL = "/directors";
    private static final String DIRECTOR_UPDATE_URL = "/directors/{id}";
    private static final String DIRECTOR_DELETE_URL = "/directors/{id}";
    private static final String DIRECTOR_SEARCH_URL = "/directors/search";
    private static final String DIRECTOR_LIST_URL = "/directors";
    private static final String DIRECTOR_MOVIES_URL = "/directors/{id}/movies";

    public static Response sendCreateDirector(
            Map<String, Object> headers,
            DirectorCreateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(DIRECTOR_CREATE_URL)
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendUpdateDirector(
            Map<String, Object> headers,
            Long id,
            DirectorUpdateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .put(DIRECTOR_UPDATE_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendDeleteDirector(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .headers(headers)
            .when()
                .delete(DIRECTOR_DELETE_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendSearchDirectors(
            Map<String, Object> headers,
            DirectorSearchRequest request) {

        return given().log().all()
                .headers(headers)
                .queryParam("query", request.query())
                .queryParam("page", request.page())
                .queryParam("size", request.size())
            .when()
                .get(DIRECTOR_SEARCH_URL)
            .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response sendGetDirectorList(
            Map<String, Object> headers,
            DirectorListRequest request) {

        return given().log().all()
                .headers(headers)
                .queryParam("page", request.page())
                .queryParam("size", request.size())
            .when()
                .get(DIRECTOR_LIST_URL)
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendGetMoviesByDirector(
            Map<String, Object> headers,
            Long id,
            DirectorMovieSearchRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .get(DIRECTOR_MOVIES_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }
} 
