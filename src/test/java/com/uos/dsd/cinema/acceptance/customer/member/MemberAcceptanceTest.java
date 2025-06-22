package com.uos.dsd.cinema.acceptance.customer.member;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.customer.member.steps.MemberSteps;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberDeleteRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.request.MemberLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.GetMemberInfoResponse;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.MemberLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.customer.member.response.MemberSignupResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.jwt.JwtClaim;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.core.security.SecurityConstants.TokenType;
import com.uos.dsd.cinema.domain.customer.common.exception.IllegalProfileImageException;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Map;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MemberAcceptanceTest extends AcceptanceTest {

    // Test data from 09_others.sql
    private static final String EXIST_MEMBER_USERNAME_1 = "user1";
    private static final String EXIST_MEMBER_USERNAME_3 = "user3";
    private static final String EXIST_MEMBER_USERNAME_5 = "user5";
    private static final String EXIST_MEMBER_USERNAME_7 = "user7";
    private static final String EXIST_MEMBER_PASSWORD = "password123!";
    
    private final JwtUtils jwtUtils;

    @Autowired
    public MemberAcceptanceTest(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Test
    void 회원_로그인() {
        /* Given */
        String username = EXIST_MEMBER_USERNAME_1;
        String password = EXIST_MEMBER_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendLoginMember(headers, 
                new MemberLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<MemberLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        // check body accessToken
        String accessToken = apiResponse.data().accessToken();
        JwtClaim accessTokenClaim = jwtUtils.getJwtClaim(accessToken);
        assertEquals(Role.MEMBER, accessTokenClaim.role());
        assertEquals(TokenType.ACCESS, accessTokenClaim.tokenType());
        // check cookie refreshToken
        String refreshToken = response.getCookie(SecurityConstants.REISSUE_COOKIE_NAME);
        JwtClaim refreshTokenClaim = jwtUtils.getJwtClaim(refreshToken);
        assertEquals(Role.MEMBER, refreshTokenClaim.role());
        assertEquals(TokenType.REFRESH, refreshTokenClaim.tokenType());
    }

    @Test
    void 회원_정보_조회() {
        /* Given */
        String username = EXIST_MEMBER_USERNAME_3;
        String password = EXIST_MEMBER_PASSWORD;
        
        /* When */
        String accessToken = getAccessToken(username, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        Response response = MemberSteps.sendGetMemberInfo(headers, username);
        log.info("response: {}", response.asString());
        ApiResponse<GetMemberInfoResponse> apiResponse = response.as(new TypeRef<ApiResponse<GetMemberInfoResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(username, apiResponse.data().username());
        assertEquals("회원3", apiResponse.data().name());
        assertEquals("010-0000-0003", apiResponse.data().phone());
    }

    @Test
    void 회원가입() {
        /* Given */
        String newUsername = "newuser123";
        String password = EXIST_MEMBER_PASSWORD;
        String name = "NewMember";
        String phone = "010-1111-2222";
        LocalDate birthDate = LocalDate.of(1995, 3, 15);

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendSignupMember(headers, newUsername, password, name, phone, birthDate, null);
        log.info("response: {}", response.asString());
        ApiResponse<MemberSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(newUsername, apiResponse.data().username());
    }

    @Test
    void 회원가입_유효한_프로필_이미지() throws IOException {
        /* Given */
        String newUsername = "newuserimg123";
        String password = EXIST_MEMBER_PASSWORD;
        String name = "NewMemberWithImage";
        String phone = "010-2222-3333";
        LocalDate birthDate = LocalDate.of(1995, 3, 15);
        File validImage = createTestImageFile("test-image.jpg", "image/jpeg");

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendSignupMember(headers, newUsername, password, name, phone, birthDate, validImage);
        log.info("response: {}", response.asString());
        ApiResponse<MemberSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(newUsername, apiResponse.data().username());
        
        // Clean up
        validImage.delete();
    }

    @ParameterizedTest
    @ValueSource(strings = {"test.txt", "test.pdf", "test.doc", "test.exe"})
    void 회원가입_유효하지_않은_파일_확장자(String filename) throws IOException {
        /* Given */
        String newUsername = "invalidfileuser" + filename.replace(".", "");
        String password = EXIST_MEMBER_PASSWORD;
        String name = "InvalidFileUser";
        String phone = "010-3333-4444";
        LocalDate birthDate = LocalDate.of(1995, 3, 15);
        File invalidFile = createTestFile(filename, "text/plain");

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendSignupMember(headers, newUsername, password, name, phone, birthDate, invalidFile);
        log.info("response: {}", response.asString());
        ApiResponse<MemberSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalProfileImageException.MESSAGE, apiResponse.message());
        
        // Clean up
        invalidFile.delete();
    }

    @Test
    void 회원가입_큰_파일_크기() throws IOException {
        /* Given */
        String newUsername = "largefileuser";
        String password = EXIST_MEMBER_PASSWORD;
        String name = "LargeFileUser";
        String phone = "010-4444-5555";
        LocalDate birthDate = LocalDate.of(1995, 3, 15);
        File largeFile = createLargeTestImageFile("large-image.jpg", "image/jpeg", 2 * 1024 * 1024 + 1); // 2MB+1

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendSignupMember(headers, newUsername, password, name, phone, birthDate, largeFile);
        log.info("response: {}", response.asString());
        ApiResponse<MemberSignupResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberSignupResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalProfileImageException.MESSAGE, apiResponse.message());
        
        // Clean up
        largeFile.delete();
    }

    @Test
    void 회원_정보_수정() {
        /* Given */
        String username = EXIST_MEMBER_USERNAME_5;
        String password = EXIST_MEMBER_PASSWORD;
        
        /* When */
        String accessToken = getAccessToken(username, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        /* Given */
        String newPassword = "newPassword123!";
        String newName = "UpdatedMember";
        String newPhone = "010-5555-5555";
        LocalDate newBirthDate = LocalDate.of(1999, 12, 25);

        /* When */
        Response response = MemberSteps.sendUpdateMember(headers, username, password, newPassword, newName, newPhone, newBirthDate, null);
        log.info("response: {}", response.asString());
        ApiResponse<?> apiResponse = response.as(new TypeRef<ApiResponse<?>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
    }

    @Test
    void 회원_정보_수정_유효한_프로필_이미지() throws IOException {
        /* Given */
        String username = "updateimguser123";
        String password = EXIST_MEMBER_PASSWORD;
        String name = "UpdateImageUser";
        String phone = "010-6666-7777";
        LocalDate birthDate = LocalDate.of(1995, 3, 15);

        // 먼저 회원가입
        Map<String, Object> signupHeaders = AuthHeaderProvider.createEmptyHeader();
        MemberSteps.sendSignupMember(signupHeaders, username, password, name, phone, birthDate, null);

        String accessToken = getAccessToken(username, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        File validImage = createTestImageFile("update-image.png", "image/png");

        /* When */
        Response response = MemberSteps.sendUpdateMember(headers, username, password, null, null, null, null, validImage);
        log.info("response: {}", response.asString());
        ApiResponse<?> apiResponse = response.as(new TypeRef<ApiResponse<?>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        
        // Clean up
        validImage.delete();
    }

    @Test
    void 회원_정보_수정_유효하지_않은_이미지() throws IOException {
        /* Given */
        String username = "invalidupdateuser123";
        String password = EXIST_MEMBER_PASSWORD;
        String name = "InvalidUpdateUser";
        String phone = "010-7777-8888";
        LocalDate birthDate = LocalDate.of(1995, 3, 15);

        // 먼저 회원가입
        Map<String, Object> signupHeaders = AuthHeaderProvider.createEmptyHeader();
        MemberSteps.sendSignupMember(signupHeaders, username, password, name, phone, birthDate, null);

        String accessToken = getAccessToken(username, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        File invalidFile = createTestFile("invalid-update.txt", "text/plain");

        /* When */
        Response response = MemberSteps.sendUpdateMember(headers, username, password, null, null, null, null, invalidFile);
        log.info("response: {}", response.asString());
        ApiResponse<?> apiResponse = response.as(new TypeRef<ApiResponse<?>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalProfileImageException.MESSAGE, apiResponse.message());
        
        // Clean up
        invalidFile.delete();
    }

    @Test
    void 회원_탈퇴() {
        /* Given */
        String username = EXIST_MEMBER_USERNAME_7;
        String password = EXIST_MEMBER_PASSWORD;
        
        /* When */
        String accessToken = getAccessToken(username, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        /* When */
        Response response = MemberSteps.sendDeleteMember(headers, username, new MemberDeleteRequest(password));
        log.info("response: {}", response.asString());
        ApiResponse<?> apiResponse = response.as(new TypeRef<ApiResponse<?>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
    }

    @Test
    void 다른_회원_정보_조회_실패() {
        /* Given */
        String username = EXIST_MEMBER_USERNAME_1;
        String password = EXIST_MEMBER_PASSWORD;
        String otherUsername = EXIST_MEMBER_USERNAME_3;

        /* When */
        String accessToken = getAccessToken(username, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        Response response = MemberSteps.sendGetMemberInfo(headers, otherUsername);
        log.info("response: {}", response.asString());
        ApiResponse<GetMemberInfoResponse> apiResponse = response.as(new TypeRef<ApiResponse<GetMemberInfoResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkForbidden(response.statusCode(), apiResponse.code());
        assertEquals("You can only get your own info", apiResponse.message());
    }

    void checkSuccess(int statusCode, String code) {
        assertEquals(200, statusCode);
        assertEquals(CommonResultCode.SUCCESS.getCode(), code);
    }

    void checkBadRequest(int statusCode, String code) {
        assertEquals(400, statusCode);
        assertEquals(CommonResultCode.BAD_REQUEST.getCode(), code);
    }

    void checkUnauthorized(int statusCode, String code) {
        assertEquals(401, statusCode);
        assertEquals(CommonResultCode.UNAUTHORIZED.getCode(), code);
    }

    void checkForbidden(int statusCode, String code) {
        assertEquals(403, statusCode);
        assertEquals(CommonResultCode.FORBIDDEN.getCode(), code);
    }

    /**
     * Login and return accessToken
     */
    String getAccessToken(String username, String password) {
        // login
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = MemberSteps.sendLoginMember(headers,
                new MemberLoginRequest(username, password));
        log.info("response: {}", response.asString());
        ApiResponse<MemberLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<MemberLoginResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);
        
        // return accessToken
        String accessToken = apiResponse.data().accessToken();
        return accessToken;
    }

    /**
     * Create a test image file for testing
     */
    private File createTestImageFile(String filename, String contentType) throws IOException {
        Path tempFile = Files.createTempFile("test-", filename);
        // Create a minimal valid image file (1x1 pixel PNG)
        byte[] imageData = {
            (byte) 0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A, // PNG signature
            0x00, 0x00, 0x00, 0x0D, 0x49, 0x48, 0x44, 0x52, // IHDR chunk
            0x00, 0x00, 0x00, 0x01, 0x00, 0x00, 0x00, 0x01, // width=1, height=1
            0x08, 0x02, 0x00, 0x00, 0x00, (byte) 0x90, (byte) 0x77, 0x53, (byte) 0xDE, // bit depth, color type, etc.
            0x00, 0x00, 0x00, 0x0C, 0x49, 0x44, 0x41, 0x54, // IDAT chunk
            0x08, (byte) 0x99, 0x01, 0x01, 0x00, 0x00, (byte) 0xFF, (byte) 0xFF, 0x00, 0x00, 0x00, 0x02, 0x00, 0x01,
            0x00, 0x00, 0x00, 0x00, 0x49, 0x45, 0x4E, 0x44, (byte) 0xAE, 0x42, 0x60, (byte) 0x82 // IEND chunk
        };
        Files.write(tempFile, imageData);
        return tempFile.toFile();
    }

    /**
     * Create a test file with specified content
     */
    private File createTestFile(String filename, String contentType) throws IOException {
        Path tempFile = Files.createTempFile("test-", filename);
        Files.write(tempFile, "This is a test file content".getBytes());
        return tempFile.toFile();
    }

    /**
     * Create a large test image file for size testing
     */
    private File createLargeTestImageFile(String filename, String contentType, int sizeBytes) throws IOException {
        Path tempFile = Files.createTempFile("test-", filename);
        byte[] data = new byte[sizeBytes];
        // Fill with some data
        for (int i = 0; i < sizeBytes; i++) {
            data[i] = (byte) (i % 256);
        }
        Files.write(tempFile, data);
        return tempFile.toFile();
    }
} 
