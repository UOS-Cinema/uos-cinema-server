package com.uos.dsd.cinema.adaptor.in.web.admin.request;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

public record AdminDeleteRequest(Long adminId, String password) {

    public AdminDeleteRequest {

        if (adminId == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Admin ID cannot be null");
        }
        if (password == null || password.isBlank()) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Password cannot be null or blank");
        }
    }
} 
