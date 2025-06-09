package com.uos.dsd.cinema.application.port.out.customer.guest.query;

import java.util.Optional;

import com.uos.dsd.cinema.domain.customer.guest.Guest;

public interface GuestQueryRepository {
    
    Optional<Guest> findByCustomerId(Long customerId);
}
