package com.uos.dsd.cinema.application.port.out.customer.guest.query;

import java.util.Optional;

import com.uos.dsd.cinema.domain.customer.guest.query.GuestInfo;

public interface GuestQueryRepository {
    
    Optional<GuestInfo> findByCustomerId(Long customerId);
}
