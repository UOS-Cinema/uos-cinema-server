package com.uos.dsd.cinema.application.port.out.customer.guest;

import com.uos.dsd.cinema.domain.customer.guest.Guest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GuestRepository {
    
    List<Guest> findAllByNameAndPhoneAndBirthDate(
        String name,
        String phone,
        LocalDate birthDate
    );

    Guest save(Guest guest);

    Optional<Guest> findByCustomerId(Long customerId);
    
    void delete(Guest guest);
} 
