package com.uos.dsd.cinema.adaptor.in.web.auth;

import static com.uos.dsd.cinema.core.security.SecurityConstants.*;

import com.uos.dsd.cinema.adaptor.in.web.auth.response.LogoutResponse;
import com.uos.dsd.cinema.adaptor.in.web.auth.response.RefreshTokenResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.jwt.JwtClaim;
import com.uos.dsd.cinema.core.jwt.JwtUtils;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;

    @PostMapping("/logout")
    public ApiResponse<LogoutResponse> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookies(request);
        JwtClaim jwtClaim = jwtUtils.getJwtClaim(refreshToken);
        Long id = jwtClaim.id();
        CookieUtil.deleteCookie(response, REISSUE_COOKIE_NAME, "/refresh-token");

        log.info("logout success, id: {}", id);       
        return ApiResponse.success(new LogoutResponse(id));
    }

    @PostMapping("/refresh-token")
    public ApiResponse<RefreshTokenResponse> refreshToken(
            HttpServletRequest request,
            HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookies(request);

        if (refreshToken != null && jwtUtils.isValidJwtToken(refreshToken)) {
            JwtClaim jwtClaim = jwtUtils.getJwtClaim(refreshToken);
            Long id = jwtClaim.id();
            Role role = jwtClaim.role();

            String newAccessToken = jwtUtils.generateAccessToken(id, role);
            String newRefreshToken = jwtUtils.generateRefreshToken(id, role);
            CookieUtil.addHttpOnlyCookie(response, REISSUE_COOKIE_NAME, newRefreshToken, jwtUtils.getRefreshTokenExpirationMs(), "/refresh-token");

            log.info("refresh token success, id: {}, role: {}", id, role);
            return ApiResponse.success(new RefreshTokenResponse(newAccessToken));
        }

        log.info("refresh token failed");
        return ApiResponse.failure(CommonResultCode.UNAUTHORIZED);
    }

    private String extractRefreshTokenFromCookies(HttpServletRequest request) {

        return CookieUtil.getCookieValue(request, REISSUE_COOKIE_NAME);
    }
}
