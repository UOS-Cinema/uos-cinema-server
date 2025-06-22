package com.uos.dsd.cinema.acceptance.customer.member.steps;

import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberDeleteRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberLoginRequest;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.File;
import java.time.LocalDate;
import java.util.Map;

public class MemberSteps {

    public static Response sendLoginMember(Map<String, Object> headers, MemberLoginRequest request) {
        return RestAssured.given()
                .headers(headers)
                .contentType("application/json")
                .body(request)
                .when()
                .post("/members/login");
    }

    public static Response sendSignupMember(Map<String, Object> headers, String username, String password, 
                                          String name, String phone, LocalDate birthDate, File profileImage) {
        var requestSpec = RestAssured.given()
                .headers(headers);

        requestSpec.multiPart("username", username);
        requestSpec.multiPart("password", password);
        requestSpec.multiPart("name", name);
        requestSpec.multiPart("phone", phone);
        requestSpec.multiPart("birthDate", birthDate.toString());

        if (profileImage != null) {
            String contentType = getContentTypeFromFileName(profileImage.getName());
            requestSpec.multiPart("profileImage", profileImage, contentType);
        }

        return requestSpec.when()
                .post("/members/signup");
    }

    public static Response sendGetMemberInfo(Map<String, Object> headers, String username) {
        return RestAssured.given()
                .headers(headers)
                .when()
                .get("/members/" + username);
    }

    public static Response sendUpdateMember(Map<String, Object> headers, String username, String password, 
                                          String newPassword, String name, String phone, LocalDate birthDate, 
                                          File profileImage) {
        var requestSpec = RestAssured.given()
                .headers(headers);

        requestSpec.multiPart("password", password);

        if (newPassword != null) {
            requestSpec.multiPart("newPassword", newPassword);
        }
        if (name != null) {
            requestSpec.multiPart("name", name);
        }
        if (phone != null) {
            requestSpec.multiPart("phone", phone);
        }
        if (birthDate != null) {
            requestSpec.multiPart("birthDate", birthDate.toString());
        }

        if (profileImage != null) {
            String contentType = getContentTypeFromFileName(profileImage.getName());
            requestSpec.multiPart("profileImage", profileImage, contentType);
        }

        return requestSpec.when()
                .put("/members/" + username);
    }

    public static Response sendDeleteMember(Map<String, Object> headers, String username, MemberDeleteRequest request) {
        return RestAssured.given()
                .headers(headers)
                .contentType("application/json")
                .body(request)
                .when()
                .delete("/members/" + username);
    }

    private static String getContentTypeFromFileName(String fileName) {
        if (fileName == null) {
            return "application/octet-stream";
        }
        
        String lowerCaseFileName = fileName.toLowerCase();
        if (lowerCaseFileName.endsWith(".jpg") || lowerCaseFileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else if (lowerCaseFileName.endsWith(".png")) {
            return "image/png";
        } else if (lowerCaseFileName.endsWith(".gif")) {
            return "image/gif";
        } else if (lowerCaseFileName.endsWith(".webp")) {
            return "image/webp";
        } else {
            return "application/octet-stream";
        }
    }

} 
