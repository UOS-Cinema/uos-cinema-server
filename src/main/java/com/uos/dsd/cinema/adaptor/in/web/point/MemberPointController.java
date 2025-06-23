package com.uos.dsd.cinema.adaptor.in.web.point;

import com.uos.dsd.cinema.application.port.in.point.response.PointHistoryResponse;
import com.uos.dsd.cinema.application.port.in.point.usecase.GetPointHistoryUseCase;
import com.uos.dsd.cinema.application.port.in.point.usecase.GetPointUseCase;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/members/points")
@RequiredArgsConstructor
public class MemberPointController {

    private final GetPointUseCase getPointUseCase;
    private final GetPointHistoryUseCase getPointHistoryUseCase;

    @GetMapping
    public ApiResponse<Integer> getPoint(
            @UserId Long userId,
            @UserRole Role role) {
        
        if (role != Role.MEMBER) {
            throw new ForbiddenException();
        }
        
        Integer point = getPointUseCase.getPoint(userId);
        return ApiResponse.success(point);
    }

    @GetMapping("/history")
    public ApiResponse<ApiResponse.PageResponse<PointHistoryResponse>> getPointHistory(
            @UserId Long userId,
            @UserRole Role role,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        
        if (role != Role.MEMBER) {
            throw new ForbiddenException();
        }
        
        Page<PointHistoryResponse> history = getPointHistoryUseCase.getPointHistory(userId, page, size);
        return ApiResponse.success(history);
    }
} 
