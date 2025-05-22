package com.uos.dsd.cinema.adaptor.in.web.admin.request;

import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;

public record AdminUpdateRequest(Long adminId, String currentPassword, String newPassword) {

    public AdminUpdateRequest {

        if (adminId == null) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Admin ID cannot be null");
        }
        if (currentPassword == null || currentPassword.isBlank()) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Current password cannot be null or blank");
        }
        if (newPassword == null || newPassword.isBlank()) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "New password cannot be null or blank");
        }
    }
} 
