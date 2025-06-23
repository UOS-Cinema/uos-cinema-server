package com.uos.dsd.cinema.application.port.in.reservation.usecase;

import com.uos.dsd.cinema.application.port.in.reservation.response.ReservationHistoryResponse;

import org.springframework.data.domain.Page;

public interface GetReservationHistoryUseCase {
    
    Page<ReservationHistoryResponse> getReservationHistory(Long customerId, int page, int size);
} 
