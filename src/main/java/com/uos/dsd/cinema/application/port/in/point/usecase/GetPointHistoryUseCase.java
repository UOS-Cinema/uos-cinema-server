package com.uos.dsd.cinema.application.port.in.point.usecase;

import com.uos.dsd.cinema.application.port.in.point.response.PointHistoryResponse;

import org.springframework.data.domain.Page;

public interface GetPointHistoryUseCase {
    
    Page<PointHistoryResponse> getPointHistory(Long customerId, int page, int size);
} 
