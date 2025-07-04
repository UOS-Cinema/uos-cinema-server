package com.uos.dsd.cinema.adaptor.in.web.auth;

import static com.uos.dsd.cinema.core.security.SecurityConstants.REISSUE_COOKIE_NAME;

import com.uos.dsd.cinema.adaptor.in.web.auth.response.LogoutResponse;
import com.uos.dsd.cinema.adaptor.in.web.auth.response.RefreshTokenResponse;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.jwt.JwtClaim;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;

    @PostMapping("/logout")
    public ApiResponse<LogoutResponse> logout(HttpServletRequest request, HttpServletResponse response) {

        String refreshToken = extractRefreshTokenFromCookies(request);
        if (refreshToken != null && jwtUtils.isValidJwtToken(refreshToken)) {
            JwtClaim jwtClaim = jwtUtils.getJwtClaim(refreshToken);
            Long id = jwtClaim.id();
            Role role = jwtClaim.role();

            CookieUtil.deleteCookie(response, REISSUE_COOKIE_NAME, "/auth", request.isSecure());

            // Refresh Token 무효화 처리

            log.info("logout success, id: {}, role: {}", id, role);
        } else {
            log.info("Already logged out (RefreshToken is already invalid)");
        }

        return ApiResponse.success();
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
            CookieUtil.addHttpOnlyCookie(response, REISSUE_COOKIE_NAME, newRefreshToken, jwtUtils.getRefreshTokenExpirationMs(), "/auth", request.isSecure());

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
