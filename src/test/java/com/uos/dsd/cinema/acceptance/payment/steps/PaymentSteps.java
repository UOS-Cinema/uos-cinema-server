package com.uos.dsd.cinema.acceptance.payment.steps;

import static io.restassured.RestAssured.given;

import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import com.uos.dsd.cinema.adaptor.in.web.payment.request.PaymentRequest;

public class PaymentSteps {

    private static final String BASE_URL = "/payments";
    private static final String DETAIL_URL = BASE_URL + "/{id}";

    public static Response payment(Map<String, Object> headers, PaymentRequest request) {
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
    
    public static Response cancelPayment(Map<String, Object> headers, Long id) {
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
