package com.uos.dsd.cinema.adaptor.in.web.admin.request;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

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
