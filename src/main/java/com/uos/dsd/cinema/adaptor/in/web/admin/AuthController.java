package com.uos.dsd.cinema.adaptor.in.web.admin;

import com.uos.dsd.cinema.adaptor.in.web.admin.request.*;
import com.uos.dsd.cinema.adaptor.in.web.admin.response.*;
import com.uos.dsd.cinema.application.port.in.admin.command.*;
import com.uos.dsd.cinema.application.port.in.admin.usecase.*;
import com.uos.dsd.cinema.common.response.ApiResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {
    
    private final SignupAdminUsecase signupAdminUsecase;
    private final LoginAdminUsecase loginAdminUsecase;
    private final UpdateAdminUsecase updateAdminUsecase;
    private final DeleteAdminUsecase deleteAdminUsecase;
    
    @PostMapping("/admin/signup")
    public ApiResponse<AdminSignupResponse> signup(@RequestBody AdminSignupRequest request) {

        log.info("signup request: {}", request.username());
        signupAdminUsecase.signup(new SignupAdminCommand(request.username(), request.password()));
        return ApiResponse.success(new AdminSignupResponse(request.username()));
    }

    @PostMapping("/admin/login")
    public ApiResponse<AdminLoginResponse> login(@RequestBody AdminLoginRequest request) {

        log.info("login request: {}", request.username());
        Long adminId = loginAdminUsecase.login(new LoginAdminCommand(request.username(), request.password()));

        // TODO: Generate JWT token and return it in the response

        return ApiResponse.success(new AdminLoginResponse(adminId.toString()));
    }

    @PutMapping("/admin/update")
    public ApiResponse<AdminUpdateResponse> update(@RequestBody AdminUpdateRequest request) {

        log.info("update request: {}", request.id());
        updateAdminUsecase.update(new UpdateAdminCommand(request.id(), request.currentPassword(), request.newPassword()));
        return ApiResponse.success(new AdminUpdateResponse(request.id()));
    }

    @DeleteMapping("/admin/delete")
    public ApiResponse<AdminDeleteResponse> delete(@RequestBody AdminDeleteRequest request) {

        log.info("delete request: {}", request.id());
        deleteAdminUsecase.delete(new DeleteAdminCommand(request.id(), request.password()));
        return ApiResponse.success(new AdminDeleteResponse(request.id()));
    }
}
