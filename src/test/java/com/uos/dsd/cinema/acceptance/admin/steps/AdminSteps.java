package com.uos.dsd.cinema.acceptance.admin.steps;

import static io.restassured.RestAssured.given;

import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminDeleteRequest;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

public class AdminSteps {

    private static final String ADMIN_SIGNUP_URL = "/admin/signup";
    private static final String ADMIN_LOGIN_URL = "/admin/login";
    private static final String ADMIN_UPDATE_URL = "/admin/update";
    private static final String ADMIN_DELETE_URL = "/admin/delete";

    public static Response sendSignupAdmin(
            Map<String, Object> headers,
            AdminSignupRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(ADMIN_SIGNUP_URL)
            .then().log().all()
                .extract()
                .response();
    }

    public static Response sendLoginAdmin(
            Map<String, Object> headers,
            AdminLoginRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .post(ADMIN_LOGIN_URL)
            .then().log().all()
                .extract()
                .response();
    }
    
    public static Response sendUpdateAdmin(
            Map<String, Object> headers,
            AdminUpdateRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .put(ADMIN_UPDATE_URL)
            .then().log().all()
                .extract()
                .response();
    }
    
    public static Response sendDeleteAdmin(
            Map<String, Object> headers,
            AdminDeleteRequest request) {

        return given().log().all()
                .headers(headers)
                .contentType(ContentType.JSON)
                .body(request)
            .when()
                .delete(ADMIN_DELETE_URL)
            .then().log().all()
                .extract()
                .response();
    }
}
