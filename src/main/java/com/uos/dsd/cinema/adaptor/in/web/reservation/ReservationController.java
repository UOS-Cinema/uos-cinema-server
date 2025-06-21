package com.uos.dsd.cinema.adaptor.in.web.reservation;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserId;
import com.uos.dsd.cinema.application.port.in.reservation.usecase.ReserveUseCase;
import com.uos.dsd.cinema.application.port.in.reservation.usecase.CancelReservationUseCase;
import com.uos.dsd.cinema.adaptor.in.web.reservation.request.ReserveRequest;
import com.uos.dsd.cinema.application.port.in.reservation.command.CancelReservationCommand;

@RequiredArgsConstructor
@RestController
@RequestMapping("/reservations")
public class ReservationController {

    private final ReserveUseCase reserveUseCase;
    private final CancelReservationUseCase cancelReservationUseCase;

    @PostMapping
    public ApiResponse<Long> reserve(@UserId Long userId, @RequestBody ReserveRequest request) {
        return ApiResponse.success(reserveUseCase.reserve(request.toCommand(userId)));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> cancel(@UserId Long userId, @PathVariable Long id) {
        cancelReservationUseCase.cancel(new CancelReservationCommand(id, userId));
        return ApiResponse.success();
    }
}
