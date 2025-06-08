package com.uos.dsd.cinema.application.port.out.customer.guest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.uos.dsd.cinema.domain.customer.guest.Guest;

public interface GuestRepository {
    
    List<Guest> findAllByNameAndPhoneAndBirthDate(
        String name,
        String phone,
        LocalDate birthDate
    );

    Optional<Guest> findById(Long id);

    Guest save(Guest guest);
    
    void delete(Guest guest);
} 
