package com.uos.dsd.cinema.adaptor.in.web.admin;

import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminDeleteRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminDeleteResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminSignupResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminUpdateResponse;
import com.uos.dsd.cinema.application.port.in.admin.command.AdminDeleteCommand;
import com.uos.dsd.cinema.application.port.in.admin.command.AdminLoginCommand;
import com.uos.dsd.cinema.application.port.in.admin.command.AdminSignupCommand;
import com.uos.dsd.cinema.application.port.in.admin.command.AdminUpdateCommand;
import com.uos.dsd.cinema.application.port.in.admin.usecase.AdminDeleteUsecase;
import com.uos.dsd.cinema.application.port.in.admin.usecase.AdminLoginUsecase;
import com.uos.dsd.cinema.application.port.in.admin.usecase.AdminSignupUsecase;
import com.uos.dsd.cinema.application.port.in.admin.usecase.AdminUpdateUsecase;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminSignupUsecase signupAdminUsecase;
    private final AdminLoginUsecase loginAdminUsecase;
    private final AdminUpdateUsecase updateAdminUsecase;
    private final AdminDeleteUsecase deleteAdminUsecase;

    private final JwtUtils jwtUtils;

    @PostMapping("/signup")
    public ApiResponse<AdminSignupResponse> signup(@RequestBody AdminSignupRequest request) {

        log.info("signup request: {}", request.username());
        signupAdminUsecase.signup(new AdminSignupCommand(request.username(), request.password()));
        return ApiResponse.success(new AdminSignupResponse(request.username()));
    }

    @PostMapping("/login")
    public ApiResponse<AdminLoginResponse> login(@RequestBody AdminLoginRequest request, HttpServletRequest httpRequest, HttpServletResponse response) {

        Long id = loginAdminUsecase.login(new AdminLoginCommand(request.username(), request.password()));
        
        String accessToken = jwtUtils.generateAccessToken(id, Role.ADMIN);
        String refreshToken = jwtUtils.generateRefreshToken(id, Role.ADMIN);
        CookieUtil.addHttpOnlyCookie(response, SecurityConstants.REISSUE_COOKIE_NAME, refreshToken, jwtUtils.getRefreshTokenExpirationMs(), "/auth", httpRequest.isSecure());

        log.info("login success, id: {}", id);
        return ApiResponse.success(new AdminLoginResponse(accessToken));
    }

    @PutMapping("/{id}")
    public ApiResponse<AdminUpdateResponse> update(
        @UserId Long requesterId,    
        @PathVariable("id") Long id,
        @RequestBody AdminUpdateRequest request) {

        log.info("update request: {}", id);

        if (!requesterId.equals(id)) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only update your own account");
        }

        updateAdminUsecase.update(new AdminUpdateCommand(id, request.currentPassword(), request.newPassword()));
        return ApiResponse.success(new AdminUpdateResponse(id));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<AdminDeleteResponse> delete(
        @UserId Long requesterId,
        @PathVariable("id") Long id,
        @RequestBody AdminDeleteRequest request) {

        log.info("delete request: {}", id);

        if (!requesterId.equals(id)) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only delete your own account");
        }

        deleteAdminUsecase.delete(new AdminDeleteCommand(id, request.password()));
        return ApiResponse.success(new AdminDeleteResponse(id));
    }
}
