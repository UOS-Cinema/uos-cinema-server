package com.uos.dsd.cinema.adaptor.in.web.guest;

import com.uos.dsd.cinema.adaptor.in.web.guest.request.GuestLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.guest.response.GuestLoginResponse;
import com.uos.dsd.cinema.application.port.in.guest.command.LoginGuestCommand;
import com.uos.dsd.cinema.application.port.in.guest.usecase.LoginGuestUsecase;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final LoginGuestUsecase loginGuestUsecase;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ApiResponse<GuestLoginResponse> login(@RequestBody GuestLoginRequest request, HttpServletResponse response) {

        Long id = loginGuestUsecase.login(
                new LoginGuestCommand(
                        request.name(),
                        request.phone(),
                        request.birthDate(),
                        request.password()));

        String accessToken = jwtUtils.generateAccessToken(id, Role.GUEST);
        String refreshToken = jwtUtils.generateRefreshToken(id, Role.GUEST);
        CookieUtil.addHttpOnlyCookie(response, SecurityConstants.REISSUE_COOKIE_NAME, refreshToken, 
                jwtUtils.getRefreshTokenExpirationMs(), "/refresh-token");

        log.info("login success, id: {}", id);
        return ApiResponse.success(new GuestLoginResponse(accessToken));
    }
}
