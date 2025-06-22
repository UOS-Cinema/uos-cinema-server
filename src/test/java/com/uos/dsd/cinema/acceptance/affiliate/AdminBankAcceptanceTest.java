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
import com.uos.dsd.cinema.adaptor.in.web.affiliate.request.CreateBankRequest;
import com.uos.dsd.cinema.adaptor.in.web.affiliate.request.UpdateBankRequest;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.affiliate.Bank;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminBankAcceptanceTest extends AcceptanceTest {

    private final String bankName = "test";
    private final String bankLogoUrl = "https://test.com/logo.png";
    private final int bankDiscountAmount = 1000;

    private final String updatedBankLogoUrl = "https://test.com/logo2.png";
    private final int updatedBankDiscountAmount = 2000;

    private final CreateBankRequest createBankRequest = new CreateBankRequest(
        bankName,
        bankLogoUrl,
        bankDiscountAmount
    );

    private final UpdateBankRequest updateBankRequest = new UpdateBankRequest(
        updatedBankLogoUrl,
        updatedBankDiscountAmount
    );

    private final String adminToken = loginAdmin();
    private final Map<String, Object> adminHeaders = AuthHeaderProvider.createAuthorizationHeader(adminToken);

    @Test
    public void createBankTest() {
        /* when */
        // 1. create bank
        Response response = createBank(adminHeaders, createBankRequest);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        // 2. get banks
        response = getBanks(adminHeaders);
        ApiResponse<List<Bank>> banksResponse =
                response.as(new TypeRef<ApiResponse<List<Bank>>>() {});
        log.info("banksResponse: {}", banksResponse);

        // then 200
        assertThat(response.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(apiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is bank name
        assertThat(apiResponse.data()).isEqualTo(bankName);
        // data contains bank name
        assertThat(banksResponse.data().stream()
                .anyMatch(b -> b.getName().equals(bankName)));
    }
    

    @Test
    public void updateCardCompanyTest() {

        /* given */
        String bankName = "신한은행";

        /* when */
        // 1. update bank
        Response updateResponse = updateBank(adminHeaders, bankName, updateBankRequest);
        ApiResponse<String> updateApiResponse =
                updateResponse.as(new TypeRef<ApiResponse<String>>() {});
        log.info("updateApiResponse: {}", updateApiResponse);

        // 2. get banks
        Response getResponse = getBanks(adminHeaders);
        ApiResponse<List<Bank>> getBanksResponse =
                getResponse.as(new TypeRef<ApiResponse<List<Bank>>>() {});
        log.info("getBanksResponse: {}", getBanksResponse);

        // then 200
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(updateApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is bank name
        assertThat(updateApiResponse.data()).isEqualTo(bankName);
        // data contains bank name
        assertThat(getBanksResponse.data().stream()
                .anyMatch(b -> b.getName().equals(bankName)
                        && b.getLogoUrl().equals(updatedBankLogoUrl)
                        && b.getDiscountAmount() == updatedBankDiscountAmount));
    }
    
    @Test
    public void deleteCardCompanyTest() {
        /* when */
        // 1. delete bank
        String bankName = "신한은행";
        Response deleteResponse = deleteBank(adminHeaders, bankName);
        ApiResponse<Void> deleteApiResponse =
                deleteResponse.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("deleteApiResponse: {}", deleteApiResponse);

        // 2. get banks
        Response getResponse = getBanks(adminHeaders);
        ApiResponse<List<Bank>> getBanksResponse =
                getResponse.as(new TypeRef<ApiResponse<List<Bank>>>() {});
        log.info("getBanksResponse: {}", getBanksResponse);

        // then 200
        assertThat(deleteResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(deleteApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is null
        assertThat(deleteApiResponse.data()).isNull();
        // data does not contain bank name
        assertThat(getBanksResponse.data().stream()
                .noneMatch(b -> b.getName().equals(bankName)));
    }
    
    private static final String BASE_URL = "/admin/banks";
    private static final String DETAIL_URL = BASE_URL + "/{name}";

    private Response createBank(Map<String, Object> headers,
            CreateBankRequest request) {
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

    private Response getBanks(Map<String, Object> headers) {
        return given().log().all()
                .headers(headers)
            .when().log().all()
                .get(BASE_URL)
            .then().log().all()
                .extract()
            .response();
    }

    private Response updateBank(Map<String, Object> headers,
            String name, UpdateBankRequest request) {
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

    private Response deleteBank(Map<String, Object> headers,
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
