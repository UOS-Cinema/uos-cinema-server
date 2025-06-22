package com.uos.dsd.cinema.acceptance.affiliate;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.adaptor.in.web.affiliate.request.CreateCardCompanyRequest;
import com.uos.dsd.cinema.adaptor.in.web.affiliate.request.UpdateCardCompanyRequest;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.affiliate.CardCompany;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminCardCompanyAcceptanceTest extends AcceptanceTest {

    private final String cardCompanyName = "test";
    private final String cardCompanyLogoUrl = "https://test.com/logo.png";
    private final int cardCompanyDiscountAmount = 1000;

    private final String updatedCardCompanyLogoUrl = "https://test.com/logo2.png";
    private final int updatedCardCompanyDiscountAmount = 2000;

    private final CreateCardCompanyRequest createCardCompanyRequest = new CreateCardCompanyRequest(
        cardCompanyName,
        cardCompanyLogoUrl,
        cardCompanyDiscountAmount
    );

    private final UpdateCardCompanyRequest updateCardCompanyRequest = new UpdateCardCompanyRequest(
        updatedCardCompanyLogoUrl,
        updatedCardCompanyDiscountAmount
    );

    private final String adminToken = loginAdmin();
    private final Map<String, Object> adminHeaders = AuthHeaderProvider.createAuthorizationHeader(adminToken);

    @Test
    public void createCardCompanyTest() {
        /* when */
        // 1. create card company
        Response response = createCardCompany(adminHeaders, createCardCompanyRequest);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        // 2. get card companies
        response = getCardCompanies(adminHeaders);
        ApiResponse<List<CardCompany>> cardCompaniesResponse =
                response.as(new TypeRef<ApiResponse<List<CardCompany>>>() {});
        log.info("cardCompaniesResponse: {}", cardCompaniesResponse);

        // then 200
        assertThat(response.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(apiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is card company name
        assertThat(apiResponse.data()).isEqualTo(cardCompanyName);
        // data contains card company name
        assertThat(cardCompaniesResponse.data().stream()
                .anyMatch(c -> c.getName().equals(cardCompanyName)));
    }
    

    @Test
    public void updateCardCompanyTest() {

        /* given */
        String cardCompanyName = "신한카드";

        /* when */
        // 1. update card company
        Response updateResponse = updateCardCompany(adminHeaders, cardCompanyName, updateCardCompanyRequest);
        ApiResponse<String> updateApiResponse =
                updateResponse.as(new TypeRef<ApiResponse<String>>() {});
        log.info("updateApiResponse: {}", updateApiResponse);

        // 2. get card companies
        Response getResponse = getCardCompanies(adminHeaders);
        ApiResponse<List<CardCompany>> getCardCompaniesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<CardCompany>>>() {});
        log.info("getCardCompaniesResponse: {}", getCardCompaniesResponse);

        // then 200
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(updateApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is card company name
        assertThat(updateApiResponse.data()).isEqualTo(cardCompanyName);
        // data contains card company name
        assertThat(getCardCompaniesResponse.data().stream()
                .anyMatch(c -> c.getName().equals(cardCompanyName)
                        && c.getLogoUrl().equals(updatedCardCompanyLogoUrl)
                        && c.getDiscountAmount() == updatedCardCompanyDiscountAmount));
    }
    
    @Test
    public void deleteCardCompanyTest() {
        /* when */
        // 1. delete card company
        String cardCompanyName = "신한카드";
        Response deleteResponse = deleteCardCompany(adminHeaders, cardCompanyName);
        ApiResponse<Void> deleteApiResponse =
                deleteResponse.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("deleteApiResponse: {}", deleteApiResponse);

        // 2. get card companies
        Response getResponse = getCardCompanies(adminHeaders);
        ApiResponse<List<CardCompany>> getCardCompaniesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<CardCompany>>>() {});
        log.info("getCardCompaniesResponse: {}", getCardCompaniesResponse);

        // then 200
        assertThat(deleteResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(deleteApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is null
        assertThat(deleteApiResponse.data()).isNull();
        // data does not contain card company name
        assertThat(getCardCompaniesResponse.data().stream()
                .noneMatch(c -> c.getName().equals(cardCompanyName)));
    }
    
    private static final String BASE_URL = "/admin/card-companies";
    private static final String DETAIL_URL = BASE_URL + "/{name}";

    private Response createCardCompany(Map<String, Object> headers,
            CreateCardCompanyRequest request) {
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

    private Response getCardCompanies(Map<String, Object> headers) {
        return given().log().all()
                .headers(headers)
            .when().log().all()
                .get(BASE_URL)
            .then().log().all()
                .extract()
            .response();
    }

    private Response updateCardCompany(Map<String, Object> headers,
            String name, UpdateCardCompanyRequest request) {
        return given().log().all()
                .headers(headers)
                .pathParam("name", name)
                .body(request)
                .contentType(ContentType.JSON)
            .when().log().all()
                .put(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }

    private Response deleteCardCompany(Map<String, Object> headers,
            String name) {
        return given().log().all()
                .headers(headers)
                .pathParam("name", name)
            .when().log().all()
                .delete(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }
}
