package com.uos.dsd.cinema.acceptance.screen_type;

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
import com.uos.dsd.cinema.adaptor.in.web.screen_type.request.CreateScreenTypeRequest;
import com.uos.dsd.cinema.adaptor.in.web.screen_type.request.UpdateScreenTypeRequest;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AdminScreenTypeAcceptanceTest extends AcceptanceTest {

    private final String screenTypeName = "test";
    private final String screenTypeIconUrl = "https://test.com/icon.png";
    private final int screenTypePrice = 1000;

    private final int updatedScreenTypePrice = 2000;

    private final CreateScreenTypeRequest createScreenTypeRequest = new CreateScreenTypeRequest(
        screenTypeName,
        screenTypeIconUrl,
        screenTypePrice
    );

    private final UpdateScreenTypeRequest updateScreenTypeRequest = new UpdateScreenTypeRequest(
        screenTypeIconUrl,
        updatedScreenTypePrice
    );

    private final String adminToken = loginAdmin();
    private final Map<String, Object> adminHeaders = AuthHeaderProvider.createAuthorizationHeader(adminToken);

    @Test
    public void createScreenTypeTest() {
        /* when */
        // 1. create screen type
        Response response = createScreenType(adminHeaders, createScreenTypeRequest);
        ApiResponse<String> apiResponse = response.as(new TypeRef<ApiResponse<String>>() {});
        log.info("apiResponse: {}", apiResponse);

        // 2. get screen types
        response = getScreenTypes(adminHeaders);
        ApiResponse<List<ScreenType>> screenTypesResponse =
                response.as(new TypeRef<ApiResponse<List<ScreenType>>>() {});
        log.info("screenTypesResponse: {}", screenTypesResponse);

        // then 200
        assertThat(response.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(apiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is screen type name
        assertThat(apiResponse.data()).isEqualTo(screenTypeName);
        // data contains screen type name
        assertThat(screenTypesResponse.data().stream()
                .anyMatch(c -> c.getType().equals(screenTypeName)));
    }
    

    @Test
    public void updateScreenTypeTest() {

        /* given */
        String screenTypeName = "2D";

        /* when */
        // 1. update screen type
        Response updateResponse = updateScreenType(adminHeaders, screenTypeName, updateScreenTypeRequest);
        ApiResponse<String> updateApiResponse =
                updateResponse.as(new TypeRef<ApiResponse<String>>() {});
        log.info("updateApiResponse: {}", updateApiResponse);

        // 2. get screen types
        Response getResponse = getScreenTypes(adminHeaders);
        ApiResponse<List<ScreenType>> getScreenTypesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<ScreenType>>>() {});
        log.info("getScreenTypesResponse: {}", getScreenTypesResponse);

        // then 200
        assertThat(updateResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(updateApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is screen type name
        assertThat(updateApiResponse.data()).isEqualTo(screenTypeName);
        // data contains screen type name
        assertThat(getScreenTypesResponse.data().stream()
                .anyMatch(c -> c.getType().equals(screenTypeName)
                        && c.getPrice() == updatedScreenTypePrice));
    }
    
    @Test
    public void deleteScreenTypeTest() {
        /* given */
        String screenTypeName = "2D";

        /* when */
        // 1. delete screen type
        Response deleteResponse = deleteScreenType(adminHeaders, screenTypeName);
        ApiResponse<Void> deleteApiResponse =
                deleteResponse.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("deleteApiResponse: {}", deleteApiResponse);

        // 2. get screen types
        Response getResponse = getScreenTypes(adminHeaders);
        ApiResponse<List<ScreenType>> getScreenTypesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<ScreenType>>>() {});
        log.info("getScreenTypesResponse: {}", getScreenTypesResponse);

        // then 400
        assertThat(deleteResponse.getStatusCode()).isEqualTo(400);
        // Bad Request
        assertThat(deleteApiResponse.code()).isEqualTo(CommonResultCode.BAD_REQUEST.getCode());
    }

    @Test
    public void deleteScreenTypeFailedTest() {
        /* given */
        String screenTypeName = "test";

        /* when */
        // 1. create screen type
        createScreenType(adminHeaders, createScreenTypeRequest);
        
        // 2. delete screen type
        Response deleteResponse = deleteScreenType(adminHeaders, screenTypeName);
        ApiResponse<Void> deleteApiResponse =
                deleteResponse.as(new TypeRef<ApiResponse<Void>>() {});
        log.info("deleteApiResponse: {}", deleteApiResponse);

        // 3. get screen types
        Response getResponse = getScreenTypes(adminHeaders);
        ApiResponse<List<ScreenType>> getScreenTypesResponse =
                getResponse.as(new TypeRef<ApiResponse<List<ScreenType>>>() {});
        log.info("getScreenTypesResponse: {}", getScreenTypesResponse);

        // then 200
        assertThat(deleteResponse.getStatusCode()).isEqualTo(200);
        // Success
        assertThat(deleteApiResponse.code()).isEqualTo(CommonResultCode.SUCCESS.getCode());
        // data is null
        assertThat(deleteApiResponse.data()).isNull();
        // data does not contain bank name
        assertThat(getScreenTypesResponse.data().stream()
                .noneMatch(c -> c.getType().equals(screenTypeName)));
    }
    
    private static final String BASE_URL = "/admin/screen-types";
    private static final String DETAIL_URL = BASE_URL + "/{type}";

    private Response createScreenType(Map<String, Object> headers,
            CreateScreenTypeRequest request) {
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

    private Response getScreenTypes(Map<String, Object> headers) {
        return given().log().all()
                .headers(headers)
            .when().log().all()
                .get(BASE_URL)
            .then().log().all()
                .extract()
            .response();
    }

    private Response updateScreenType(Map<String, Object> headers,
            String type, UpdateScreenTypeRequest request) {
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

    private Response deleteScreenType(Map<String, Object> headers,
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
