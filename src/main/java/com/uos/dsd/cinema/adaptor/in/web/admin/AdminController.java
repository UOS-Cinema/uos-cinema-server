package com.uos.dsd.cinema.adaptor.in.web.admin;

import com.uos.dsd.cinema.adaptor.in.web.admin.request.*;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.*;
import com.uos.dsd.cinema.application.port.in.admin.command.*;
import com.uos.dsd.cinema.application.port.in.admin.usecase.*;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.utils.CookieUtil;
import com.uos.dsd.cinema.core.jwt.JwtUtils;
import com.uos.dsd.cinema.core.security.CustomUserDetails;
import com.uos.dsd.cinema.core.security.SecurityConstants;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AdminController {

    private final SignupAdminUsecase signupAdminUsecase;
    private final LoginAdminUsecase loginAdminUsecase;
    private final UpdateAdminUsecase updateAdminUsecase;
    private final DeleteAdminUsecase deleteAdminUsecase;

    private final JwtUtils jwtUtils;

    @PostMapping("/admin/signup")
    public ApiResponse<AdminSignupResponse> signup(@RequestBody AdminSignupRequest request) {

        log.info("signup request: {}", request.username());
        signupAdminUsecase.signup(new SignupAdminCommand(request.username(), request.password()));
        return ApiResponse.success(new AdminSignupResponse(request.username()));
    }

    @PostMapping("/admin/login")
    public ApiResponse<AdminLoginResponse> login(@RequestBody AdminLoginRequest request, HttpServletResponse response) {

        Long id = loginAdminUsecase.login(new LoginAdminCommand(request.username(), request.password()));
        
        String accessToken = jwtUtils.generateAccessToken(id, Role.ADMIN);
        String refreshToken = jwtUtils.generateRefreshToken(id, Role.ADMIN);
        CookieUtil.addHttpOnlyCookie(response, SecurityConstants.REISSUE_COOKIE_NAME, refreshToken, jwtUtils.getRefreshTokenExpirationMs(), "/refresh-token");

        log.info("login success, id: {}", id);
        return ApiResponse.success(new AdminLoginResponse(accessToken));
    }

    @PutMapping("/admin/update")
    public ApiResponse<AdminUpdateResponse> update(@RequestBody AdminUpdateRequest request) {

        log.info("update request: {}", request.id());

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long requesterId = userDetails.getId();
        if (!requesterId.equals(request.id())) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only update your own account");
        }

        updateAdminUsecase.update(new UpdateAdminCommand(request.id(), request.currentPassword(), request.newPassword()));
        return ApiResponse.success(new AdminUpdateResponse(request.id()));
    }

    @DeleteMapping("/admin/delete")
    public ApiResponse<AdminDeleteResponse> delete(@RequestBody AdminDeleteRequest request) {

        log.info("delete request: {}", request.id());

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long requesterId = userDetails.getId();
        if (!requesterId.equals(request.id())) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only delete your own account");
        }

        deleteAdminUsecase.delete(new DeleteAdminCommand(request.id(), request.password()));
        return ApiResponse.success(new AdminDeleteResponse(request.id()));
    }
}
