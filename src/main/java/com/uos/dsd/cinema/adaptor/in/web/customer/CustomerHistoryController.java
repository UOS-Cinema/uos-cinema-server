package com.uos.dsd.cinema.adaptor.in.web.customer;

import com.uos.dsd.cinema.application.port.in.payment.response.PaymentHistoryResponse;
import com.uos.dsd.cinema.application.port.in.payment.usecase.GetPaymentHistoryUseCase;
import com.uos.dsd.cinema.application.port.in.reservation.response.ReservationHistoryResponse;
import com.uos.dsd.cinema.application.port.in.reservation.usecase.GetReservationHistoryUseCase;
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
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerHistoryController {

    private final GetReservationHistoryUseCase getReservationHistoryUseCase;
    private final GetPaymentHistoryUseCase getPaymentHistoryUseCase;

    @GetMapping("/reservations")
    public ApiResponse<ApiResponse.PageResponse<ReservationHistoryResponse>> getReservationHistory(
            @UserId Long userId,
            @UserRole Role role,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        
        // 회원과 비회원 모두 가능
        if (role != Role.MEMBER && role != Role.GUEST) {
            return ApiResponse.success(Page.empty());
        }
        
        Page<ReservationHistoryResponse> history = getReservationHistoryUseCase.getReservationHistory(userId, page, size);
        return ApiResponse.success(history);
    }

    @GetMapping("/payments")
    public ApiResponse<ApiResponse.PageResponse<PaymentHistoryResponse>> getPaymentHistory(
            @UserId Long userId,
            @UserRole Role role,
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        
        // 회원과 비회원 모두 가능
        if (role != Role.MEMBER && role != Role.GUEST) {
            return ApiResponse.success(Page.empty());
        }
        
        Page<PaymentHistoryResponse> history = getPaymentHistoryUseCase.getPaymentHistory(userId, page, size);
        return ApiResponse.success(history);
    }
} 
