package com.uos.dsd.cinema.acceptance.customer_type;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Order;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.common.mapper.TypeRef;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.adaptor.in.web.customer_type.request.CreateCustomerTypeRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer_type.request.UpdateCustomerTypeRequest;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.customer_type.CustomerType;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

@DisplayNameGeneration(ReplaceUnderscores.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AdminCustomerTypeAcceptanceTest extends AcceptanceTest {

    private final String customerTypeName = "test";
    private final int customerTypeDiscountAmount = 1000;

    private final int updatedCustomerTypeDiscountAmount = 2000;

    private final CreateCustomerTypeRequest createCustomerTypeRequest = new CreateCustomerTypeRequest(
        customerTypeName,
        customerTypeDiscountAmount
    );

    private final UpdateCustomerTypeRequest updateCustomerTypeRequest = new UpdateCustomerTypeRequest(
        updatedCustomerTypeDiscountAmount
    );

    private final String adminToken = loginAdmin();
    private final Map<String, Object> adminHeaders = AuthHeaderProvider.createAuthorizationHeader(adminToken);

    @Test
    @Order(1)
    public void createCustomerTypeTest() {
        /* when */
        // 1. create customer type
        Response response = createCustomerType(adminHeaders, createCustomerTypeRequest);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        // 2. get customer types
        response = getCustomerTypes(adminHeaders);
        ApiResponse<List<CustomerType>> customerTypesResponse =
                response.as(new TypeRef<ApiResponse<List<CustomerType>>>() {});
        log.info("customerTypesResponse: {}", customerTypesResponse);

        // then 200
        assertThat(response.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(apiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is customer type name
        assertThat(apiResponse.data()).isEqualTo(customerTypeName);
        // data contains customer type name
        assertThat(customerTypesResponse.data().stream()
                .anyMatch(c -> c.getType().equals(customerTypeName)));
    }
    

    @Test
    @Order(2)
    public void updateCustomerTypeTest() {

        /* given */
        String customerTypeName = "CHILD";

        /* when */
        // 1. update bank
        Response updateResponse = updateCustomerType(adminHeaders, customerTypeName, updateCustomerTypeRequest);
        ApiResponse<String> updateApiResponse =
                updateResponse.as(new TypeRef<ApiResponse<String>>() {});
        log.info("updateApiResponse: {}", updateApiResponse);

        // 2. get customer types
        Response getResponse = getCustomerTypes(adminHeaders);
        ApiResponse<List<CustomerType>> getCustomerTypesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<CustomerType>>>() {});
        log.info("getCustomerTypesResponse: {}", getCustomerTypesResponse);

        // then 200
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(updateApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is customer type name
        assertThat(updateApiResponse.data()).isEqualTo(customerTypeName);
        // data contains customer type name
        assertThat(getCustomerTypesResponse.data().stream()
                .anyMatch(c -> c.getType().equals(customerTypeName)
                        && c.getDiscountAmount() == updatedCustomerTypeDiscountAmount));
    }
    
    @Test
    @Order(3)
    public void deleteCustomerTypeTest() {
        /* when */
        // 1. delete customer type
        String customerTypeName = "CHILD";
        Response deleteResponse = deleteCustomerType(adminHeaders, customerTypeName);
        ApiResponse<Void> deleteApiResponse =
                deleteResponse.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("deleteApiResponse: {}", deleteApiResponse);

        // 2. get customer types
        Response getResponse = getCustomerTypes(adminHeaders);
        ApiResponse<List<CustomerType>> getCustomerTypesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<CustomerType>>>() {});
        log.info("getCustomerTypesResponse: {}", getCustomerTypesResponse);

        // then 200
        assertThat(deleteResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(deleteApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is null
        assertThat(deleteApiResponse.data()).isNull();
        // data does not contain bank name
        assertThat(getCustomerTypesResponse.data().stream()
                .noneMatch(c -> c.getType().equals(customerTypeName)));
    }
    
    private static final String BASE_URL = "/admin/customer-types";
    private static final String DETAIL_URL = BASE_URL + "/{type}";

    private Response createCustomerType(Map<String, Object> headers,
            CreateCustomerTypeRequest request) {
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

    private Response getCustomerTypes(Map<String, Object> headers) {
        return given().log().all()
                .headers(headers)
            .when().log().all()
                .get(BASE_URL)
            .then().log().all()
                .extract()
            .response();
    }

    private Response updateCustomerType(Map<String, Object> headers,
            String type, UpdateCustomerTypeRequest request) {
        return given().log().all()
                .headers(headers)
                .pathParam("type", type)
                .body(request)
                .contentType(ContentType.JSON)
            .when().log().all()
                .put(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }

    private Response deleteCustomerType(Map<String, Object> headers,
            String type) {
        return given().log().all()
                .headers(headers)
                .pathParam("type", type)
            .when().log().all()
                .delete(DETAIL_URL)
            .then().log().all()
                .extract()
            .response();
    }
}
