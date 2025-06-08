package com.uos.dsd.cinema.application.service.customer.guest.query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uos.dsd.cinema.application.port.in.customer.guest.query.get.GetGuestInfoQuery;
import com.uos.dsd.cinema.application.port.in.customer.guest.query.get.GetGuestInfoUsecase;
import com.uos.dsd.cinema.application.port.in.customer.guest.query.get.GuestInfoResponse;
import com.uos.dsd.cinema.application.port.out.customer.guest.query.GuestQueryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestQueryService implements GetGuestInfoUsecase {
    
    private final GuestQueryRepository guestRepository;

    @Transactional(readOnly = true)
    @Override
    public GuestInfoResponse getGuestInfo(GetGuestInfoQuery query) {
        
        return GuestInfoResponse.from(guestRepository.findByCustomerId(query.customerId())
            .orElseThrow(() -> new IllegalArgumentException("Guest not found with customer id: " + query.customerId())));
    }
}
