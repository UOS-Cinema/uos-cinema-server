package com.uos.dsd.cinema.acceptance.genre;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import com.uos.dsd.cinema.adaptor.in.web.genre.request.CreateGenreRequest;
import com.uos.dsd.cinema.adaptor.in.web.genre.request.UpdateGenreRequest;

public class AdminGenreSteps {

    private static final String BASE_URI = "/admin/genres";
    private static final String DETAIL_URI = BASE_URI + "/{name}";

    public static Response createGenre(Map<String, Object> headers, CreateGenreRequest request) {
        return given()
                .headers(headers)
                .body(request)
                .contentType(ContentType.JSON)
            .when()
                .post(BASE_URI)
            .then().log().all()
                .extract()
            .response();
    }
    
    
    public static Response updateGenre(Map<String, Object> headers, String genre, UpdateGenreRequest request) {
        return given()
                .headers(headers)
                .pathParam("name", genre)
                .body(request)
                .contentType(ContentType.JSON)
            .when()
                .put(DETAIL_URI, genre)
            .then().log().all()
                .extract()
            .response();
    }
    
    public static Response deleteGenre(Map<String, Object> headers, String genre) {
        return given()
                .headers(headers)
                .pathParam("name", genre)
            .when()
                .delete(DETAIL_URI, genre)
            .then().log().all()
                .extract()
            .response();
    }
}