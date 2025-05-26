package com.uos.dsd.cinema.acceptance.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.acceptance.auth.steps.AuthSteps;
import com.uos.dsd.cinema.adaptor.in.web.auth.response.RefreshTokenResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.jwt.JwtClaim;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.utils.AuthHeaderProvider;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class AuthAcceptanceTest extends AcceptanceTest {

    private final JwtUtils jwtUtils;

    @Autowired
    public AuthAcceptanceTest(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Test
    public void logout() {

        /* Given */
        String accessToken = jwtUtils.generateAccessToken(1L, Role.MEMBER);
        String refreshToken = jwtUtils.generateRefreshToken(1L, Role.MEMBER);

        /* When */
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);
        Map<String, Object> cookies = new HashMap<>();
        cookies.put(SecurityConstants.REISSUE_COOKIE_NAME, refreshToken);

        Response response = AuthSteps.sendLogout(headers, cookies);

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // cookie: refreshToken 삭제, 만료
        String refreshTokenValue = response.getCookie(SecurityConstants.REISSUE_COOKIE_NAME);
        assertTrue(refreshTokenValue == null || refreshTokenValue.isEmpty());
    }

    @Test
    public void refreshToken() throws InterruptedException {

        /* Given */
        String accessToken = jwtUtils.generateAccessToken(1L, Role.MEMBER);
        String refreshToken = jwtUtils.generateRefreshToken(1L, Role.MEMBER);
        JwtClaim accessTokenClaim = jwtUtils.getJwtClaim(accessToken);
        Date accessTokenExpiration = accessTokenClaim.expiration();
        JwtClaim refreshTokenClaim = jwtUtils.getJwtClaim(refreshToken);
        Date refreshTokenExpiration = refreshTokenClaim.expiration();

        /* When */
        Thread.sleep(1000);
        Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(accessToken);
        Map<String, Object> cookies = new HashMap<>();
        cookies.put(SecurityConstants.REISSUE_COOKIE_NAME, refreshToken);

        Response response = AuthSteps.sendRefreshToken(headers, cookies);
        ApiResponse<RefreshTokenResponse> apiResponse = response.as(new TypeRef<ApiResponse<RefreshTokenResponse>>() {});

        /* Then */
        // status code: 200
        assertEquals(200, response.statusCode());
        // body: 만료 일자가 더 긴 새로운 accessToken
        String newAccessToken = apiResponse.data().accessToken();
        JwtClaim newAccessTokenClaim = jwtUtils.getJwtClaim(newAccessToken);
        Date newAccessTokenExpiration = newAccessTokenClaim.expiration();
        assertTrue(newAccessTokenExpiration.after(accessTokenExpiration));
        // cookie: 만료 일자가 더 긴 새로운 refreshToken
        String newRefreshToken = response.getCookie(SecurityConstants.REISSUE_COOKIE_NAME);
        JwtClaim newRefreshTokenClaim = jwtUtils.getJwtClaim(newRefreshToken);
        Date newRefreshTokenExpiration = newRefreshTokenClaim.expiration();
        assertTrue(newRefreshTokenExpiration.after(refreshTokenExpiration));
    }
}
