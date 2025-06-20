package com.uos.dsd.cinema.acceptance.customer.guest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.customer.guest.steps.GuestSteps;
import com.uos.dsd.cinema.adaptor.in.web.customer.guest.request.GuestLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.guest.response.GetGuestInfoResponse;
import com.uos.dsd.cinema.adaptor.in.web.customer.guest.response.GuestLoginResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.jwt.JwtClaim;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.core.security.SecurityConstants.TokenType;
import com.uos.dsd.cinema.domain.common.exception.IllegalPasswordException;
import com.uos.dsd.cinema.domain.customer.common.exception.IllegalBirthDateException;
import com.uos.dsd.cinema.domain.customer.common.exception.IllegalNameException;
import com.uos.dsd.cinema.domain.customer.common.exception.IllegalPhoneException;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Stream;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class GuestAcceptanceTest extends AcceptanceTest {

    private static final Long EXIST_GUEST_ID = -1L;
    private static final String EXIST_GUEST_NAME = "테스트계정";
    private static final String EXIST_GUEST_PHONE = "01012345678";
    private static final LocalDate EXIST_GUEST_BIRTH_DATE = LocalDate.of(1990, 1, 1);
    private static final String EXIST_GUEST_PASSWORD = "password123!";

    private static final Long EXIST_GUEST_ID_2 = -2L;
    private static final String EXIST_GUEST_NAME_2 = "두번째테스트계정";
    private static final String EXIST_GUEST_PHONE_2 = "01099999999";
    private static final LocalDate EXIST_GUEST_BIRTH_DATE_2 = LocalDate.of(1995, 12, 25);
    private static final String EXIST_GUEST_PASSWORD_2 = "password123!";

    private static final String NOT_EXIST_GUEST_NAME = "새로운 테스트계정";
    private static final String NOT_EXIST_GUEST_PHONE = "01099999999";
    private static final LocalDate NOT_EXIST_GUEST_BIRTH_DATE = LocalDate.of(1995, 12, 25);
    private static final String NOT_EXIST_GUEST_PASSWORD = "newpassword123!";

    private final JwtUtils jwtUtils;

    @Autowired
    public GuestAcceptanceTest(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Test
    public void loginWithExistingGuest() {

        /* Given */
        Long id = EXIST_GUEST_ID;
        String name = EXIST_GUEST_NAME;
        String phone = EXIST_GUEST_PHONE;
        LocalDate birthDate = EXIST_GUEST_BIRTH_DATE;
        String password = EXIST_GUEST_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        // check body accessToken
        String accessToken = apiResponse.data().accessToken();
        JwtClaim accessTokenClaim = jwtUtils.getJwtClaim(accessToken);
        assertEquals(id, accessTokenClaim.id());
        assertEquals(Role.GUEST, accessTokenClaim.role());
        assertEquals(TokenType.ACCESS, accessTokenClaim.tokenType());
        // check cookie refreshToken
        String refreshToken = response.getCookie(SecurityConstants.REISSUE_COOKIE_NAME);
        JwtClaim refreshTokenClaim = jwtUtils.getJwtClaim(refreshToken);
        assertEquals(id, refreshTokenClaim.id());
        assertEquals(Role.GUEST, refreshTokenClaim.role());
        assertEquals(TokenType.REFRESH, refreshTokenClaim.tokenType());
    }

    private static Stream<Arguments> provideNotExistGuest() {
        // name, phone, birthDate, password 중 하나만 일치하지 않는 경우 생성
        return Stream.of(
                Arguments.of(NOT_EXIST_GUEST_NAME, EXIST_GUEST_PHONE, EXIST_GUEST_BIRTH_DATE, EXIST_GUEST_PASSWORD),
                Arguments.of(EXIST_GUEST_NAME, NOT_EXIST_GUEST_PHONE, EXIST_GUEST_BIRTH_DATE, EXIST_GUEST_PASSWORD),
                Arguments.of(EXIST_GUEST_NAME, EXIST_GUEST_PHONE, NOT_EXIST_GUEST_BIRTH_DATE, EXIST_GUEST_PASSWORD),
                Arguments.of(EXIST_GUEST_NAME, EXIST_GUEST_PHONE, EXIST_GUEST_BIRTH_DATE, NOT_EXIST_GUEST_PASSWORD));
    }

    @ParameterizedTest
    @MethodSource("provideNotExistGuest")
    public void loginNewGuest(String name, String phone, LocalDate birthDate, String password) {

        /* Given */
        // provide by method source

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        // check body accessToken
        String accessToken = apiResponse.data().accessToken();
        JwtClaim accessTokenClaim = jwtUtils.getJwtClaim(accessToken);
        assertNotEquals(EXIST_GUEST_ID, accessTokenClaim.id());
        assertEquals(Role.GUEST, accessTokenClaim.role());
        assertEquals(TokenType.ACCESS, accessTokenClaim.tokenType());
        // check cookie refreshToken
        String refreshToken = response.getCookie(SecurityConstants.REISSUE_COOKIE_NAME);
        JwtClaim refreshTokenClaim = jwtUtils.getJwtClaim(refreshToken);
        assertNotEquals(EXIST_GUEST_ID, refreshTokenClaim.id());
        assertEquals(Role.GUEST, refreshTokenClaim.role());
        assertEquals(TokenType.REFRESH, refreshTokenClaim.tokenType());
    }

    @ParameterizedTest
    @ValueSource(strings = { "a", "longlonglonglonglonglonglonglonglonglonglonglonglon", "123", "name123", "이름  공백" })
    public void loginWithInvalidName(String name) {

        /* Given */
        String phone = EXIST_GUEST_PHONE;
        LocalDate birthDate = EXIST_GUEST_BIRTH_DATE;
        String password = EXIST_GUEST_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalNameException.MESSAGE, apiResponse.message());
    }

    @ParameterizedTest
    @ValueSource(strings = { "01112345678", "010-1234-567", "010-12345-6789", "0101234567", "010123456789",
            "02012345678", "phone", "010-abcd-efgh", "010 1234 5678" })
    public void loginWithInvalidPhone(String phone) {

        /* Given */
        String name = EXIST_GUEST_NAME;
        LocalDate birthDate = EXIST_GUEST_BIRTH_DATE;
        String password = EXIST_GUEST_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalPhoneException.MESSAGE, apiResponse.message());
    }

    private static Stream<Arguments> provideInvalidBirthDate() {
        return Stream.of(
                Arguments.of(LocalDate.of(1899, 12, 31)), // 1900년 이전
                Arguments.of(LocalDate.now().plusDays(2)) // 미래 날짜
        );
    }

    @ParameterizedTest
    @MethodSource("provideInvalidBirthDate")
    public void loginWithInvalidBirthDate(LocalDate birthDate) {

        /* Given */
        String name = EXIST_GUEST_NAME;
        String phone = EXIST_GUEST_PHONE;
        String password = EXIST_GUEST_PASSWORD;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalBirthDateException.MESSAGE, apiResponse.message());
    }

    @ParameterizedTest
    @ValueSource(strings = { "12345678", "abcdefgh", "!@#$%^&*()_+", "nospecials123", "!@!@1234", "nodigits!!!",
            "short!1", "한글", "space space", "toolongpasswordtoolongpassword123!" })
    public void loginWithInvalidPassword(String password) {

        /* Given */
        String name = EXIST_GUEST_NAME;
        String phone = EXIST_GUEST_PHONE;
        LocalDate birthDate = EXIST_GUEST_BIRTH_DATE;

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkBadRequest(response.statusCode(), apiResponse.code());
        assertEquals(IllegalPasswordException.MESSAGE, apiResponse.message());
    }

    @Test
    public void getGuestInfo() {

        /* Given */
        Long id = EXIST_GUEST_ID;
        String name = EXIST_GUEST_NAME;
        String phone = EXIST_GUEST_PHONE;
        LocalDate birthDate = EXIST_GUEST_BIRTH_DATE;

        /* When */
        String accessToken = getAccessToken(name, phone, birthDate, EXIST_GUEST_PASSWORD);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        Response response = GuestSteps.sendGetGuestInfo(headers, id);
        log.info("response: {}", response.asString());
        ApiResponse<GetGuestInfoResponse> apiResponse = response.as(new TypeRef<ApiResponse<GetGuestInfoResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkSuccess(response.statusCode(), apiResponse.code());
        assertEquals(name, apiResponse.data().name());
        assertEquals(phone, apiResponse.data().phone());
        assertEquals(birthDate, apiResponse.data().birthDate());
    }

    @Test
    public void getGuestInfoWithOtherGuest() {

        /* Given */
        Long id = EXIST_GUEST_ID;
        String name = EXIST_GUEST_NAME_2;
        String phone = EXIST_GUEST_PHONE_2;
        LocalDate birthDate = EXIST_GUEST_BIRTH_DATE_2;
        String password = EXIST_GUEST_PASSWORD_2;

        /* When */
        String accessToken = getAccessToken(name, phone, birthDate, password);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        Response response = GuestSteps.sendGetGuestInfo(headers, id);
        log.info("response: {}", response.asString());
        ApiResponse<GetGuestInfoResponse> apiResponse = response.as(new TypeRef<ApiResponse<GetGuestInfoResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkForbidden(response.statusCode(), apiResponse.code());
        assertEquals("You can only get your own info", apiResponse.message());
    }

    @Test
    public void getGuestInfoWithInvalidToken() {

        /* Given */
        Long id = EXIST_GUEST_ID;

        /* When */
        String accessToken = "invalidToken";
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);

        Response response = GuestSteps.sendGetGuestInfo(headers, id);
        log.info("response: {}", response.asString());
        ApiResponse<GetGuestInfoResponse> apiResponse = response.as(new TypeRef<ApiResponse<GetGuestInfoResponse>>() {});
        log.info("ApiResponse: {}", apiResponse);

        /* Then */
        checkUnauthorized(response.statusCode(), apiResponse.code());
        assertEquals("Full authentication is required to access this resource", apiResponse.message());
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
    String getAccessToken(String name, String phone, LocalDate birthDate, String password) {
        
        // login
        Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
        Response response = GuestSteps.sendLoginGuest(headers,
                new GuestLoginRequest(name, phone, birthDate, password));
        log.info("response: {}", response.asString());
        ApiResponse<GuestLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<GuestLoginResponse>>() {
        });
        log.info("ApiResponse: {}", apiResponse);
        
        // return accessToken
        String accessToken = apiResponse.data().accessToken();
        return accessToken;
    }
}
