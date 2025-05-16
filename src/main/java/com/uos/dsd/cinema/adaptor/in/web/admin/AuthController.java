package com.uos.dsd.cinema.adaptor.in.web.admin;

import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminSignupRequest;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminSignupResponse;
import com.uos.dsd.cinema.application.service.admin.AuthService;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.admin.Admin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final AuthService authService;
    
    @PostMapping("/admin/signup")
    public ApiResponse<AdminSignupResponse> signup(@RequestBody AdminSignupRequest request) {

        log.info("signup request: {}", request.name());
        authService.signupAdmin(request.name(), request.password());
        return ApiResponse.success(new AdminSignupResponse(request.name()));
    }

    @PostMapping("/admin/login")
    public ApiResponse<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {

        log.info("login request: {}", request.name());
        Admin admin = authService.loginAdmin(request.name(), request.password());

        // TODO: Generate JWT token and return it in the response

        return ApiResponse.success(new AdminLoginResponse(admin.getName()));
    }
}
