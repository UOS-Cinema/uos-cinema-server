package com.uos.dsd.cinema.adaptor.in.web.payment;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.adaptor.in.web.payment.request.PaymentRequest;
import com.uos.dsd.cinema.application.service.payment.PaymentService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ApiResponse<Long> payment(
            @UserId Long userId,
            @RequestBody PaymentRequest request) {

        return ApiResponse.success(paymentService.payment(request.toCommand(userId)));
    }
}
