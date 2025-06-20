package com.uos.dsd.cinema.acceptance.movie.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieListRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieSearchRequest;
import com.uos.dsd.cinema.adaptor.in.web.movie.request.MovieUpdateRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class MovieSteps {

    private static final String MOVIE_CREATE_URL = "/movies";
    private static final String MOVIE_UPDATE_URL = "/movies/{id}";
    private static final String MOVIE_DELETE_URL = "/movies/{id}";
    private static final String MOVIE_DETAIL_URL = "/movies/{id}";
    private static final String MOVIE_SIMPLE_URL = "/movies/{id}/simple";
    private static final String MOVIE_SEARCH_URL = "/movies/search";
    private static final String MOVIE_NOW_PLAYING_URL = "/movies/now-playing";
    private static final String MOVIE_UPCOMING_URL = "/movies/upcoming";

    public static Response sendCreateMovie(
            Map<String, Object> headers,
            MovieCreateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(MOVIE_CREATE_URL)
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendUpdateMovie(
            Map<String, Object> headers,
            Long id,
            MovieUpdateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .put(MOVIE_UPDATE_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendDeleteMovie(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .headers(headers)
            .when()
                .delete(MOVIE_DELETE_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendGetMovieDetail(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .headers(headers)
            .when()
                .get(MOVIE_DETAIL_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendGetMovieSimple(
            Map<String, Object> headers,
            Long id) {

        return given().log().all()
                .headers(headers)
            .when()
                .get(MOVIE_SIMPLE_URL.replace("{id}", id.toString()))
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendSearchMovies(
            Map<String, Object> headers,
            MovieSearchRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .get(MOVIE_SEARCH_URL)
            .then()
                .log().all()
                .extract()
                .response();
    }

    public static Response sendGetNowPlayingMovies(
            Map<String, Object> headers,
            MovieListRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .get(MOVIE_NOW_PLAYING_URL)
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendGetUpcomingMovies(
            Map<String, Object> headers,
            MovieListRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .get(MOVIE_UPCOMING_URL)
            .then().log().all()
                .extract()
                .response();
    }
} 
