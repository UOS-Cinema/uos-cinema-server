package com.uos.dsd.cinema.application.service.point;

import com.uos.dsd.cinema.application.port.in.point.response.PointHistoryResponse;
import com.uos.dsd.cinema.application.port.in.point.usecase.GetPointHistoryUseCase;
import com.uos.dsd.cinema.application.port.in.point.usecase.GetPointUseCase;
import com.uos.dsd.cinema.application.port.out.point.PointTransactionRepository;
import com.uos.dsd.cinema.domain.point.PointTransaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PointService implements GetPointUseCase, GetPointHistoryUseCase {

    private final PointTransactionRepository pointTransactionRepository;

    @Override
    public Integer getPoint(Long customerId) {
        return pointTransactionRepository.findFirstByCustomerIdOrderByIdDesc(customerId)
            .map(PointTransaction::getTotalPoint)
            .orElse(0);
    }

    @Override
    public Page<PointHistoryResponse> getPointHistory(Long customerId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return pointTransactionRepository.findByCustomerId(customerId, pageRequest)
            .map(pt -> new PointHistoryResponse(
                pt.getPaymentId(),
                pt.getPoint(),
                pt.getTotalPoint()
            ));
    }
} 
