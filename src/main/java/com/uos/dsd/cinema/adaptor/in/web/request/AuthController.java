package com.uos.dsd.cinema.adaptor.in.web.request;

import com.uos.dsd.cinema.application.service.AuthService;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.model.Admin;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;

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
    
    // DTOs
    public record AdminLoginRequest(String name, String password) {

        public AdminLoginRequest {

            if (name == null || name.isBlank()) {
                throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Name cannot be null or blank");
            }
            if (password == null || password.isBlank()) {
                throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Password cannot be null or blank");
            }
        }
    }
    public record AdminSignupRequest(String name, String password) {

        public AdminSignupRequest {

            if (name == null || name.isBlank()) {
                throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Name cannot be null or blank");
            }
            if (password == null || password.isBlank()) {
                throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Password cannot be null or blank");
            }
        }
    }

    public record AdminLoginResponse(String name) { }

    public record AdminSignupResponse(String name) { }

}
