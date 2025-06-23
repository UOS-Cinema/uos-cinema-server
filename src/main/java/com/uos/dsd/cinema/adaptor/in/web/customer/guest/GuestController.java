package com.uos.dsd.cinema.adaptor.in.web.customer.guest;

import com.uos.dsd.cinema.adaptor.in.web.customer.guest.request.GuestLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.customer.guest.response.GetGuestInfoResponse;
import com.uos.dsd.cinema.adaptor.in.web.customer.guest.response.GuestLoginResponse;
import com.uos.dsd.cinema.application.port.in.customer.guest.command.LoginGuestCommand;
import com.uos.dsd.cinema.application.port.in.customer.guest.query.GetGuestInfoQuery;
import com.uos.dsd.cinema.application.port.in.customer.guest.response.GuestInfoResponse;
import com.uos.dsd.cinema.application.port.in.customer.guest.usecase.GetGuestInfoUsecase;
import com.uos.dsd.cinema.application.port.in.customer.guest.usecase.LoginGuestUsecase;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/guests")
@RequiredArgsConstructor
public class GuestController {

    private final LoginGuestUsecase loginGuestUsecase;
    private final GetGuestInfoUsecase getGuestInfoUsecase;
    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ApiResponse<GuestLoginResponse> login(@RequestBody GuestLoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {

        Long id = loginGuestUsecase.login(
                new LoginGuestCommand(
                        request.name(),
                        request.phone(),
                        request.birthDate(),
                        request.password()));

        String accessToken = jwtUtils.generateAccessToken(id, Role.GUEST);
        String refreshToken = jwtUtils.generateRefreshToken(id, Role.GUEST);
        CookieUtil.addHttpOnlyCookie(response, SecurityConstants.REISSUE_COOKIE_NAME, refreshToken, 
                jwtUtils.getRefreshTokenExpirationMs(), "/auth", httpRequest.isSecure());

        log.info("login success, id: {}", id);
        return ApiResponse.success(new GuestLoginResponse(id, accessToken));
    }

    @GetMapping("/info")
    public ApiResponse<GetGuestInfoResponse> getGuestInfo(
        @UserId Long id) {

        GuestInfoResponse guest = getGuestInfoUsecase.getGuestInfo(new GetGuestInfoQuery(id));
        GetGuestInfoResponse response = new GetGuestInfoResponse(
            guest.name(),
            guest.phone(),
            guest.birthDate()
        );

        log.info("get guest info success, id: {}", id);
        return ApiResponse.success(response);
    }
}
