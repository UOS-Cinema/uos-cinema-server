package com.uos.dsd.cinema.application.port.out.repository.guest;

import java.time.LocalDate;
import java.util.List;

import com.uos.dsd.cinema.domain.guest.Guest;

public interface GuestRepository {
    
    List<Guest> findAllByNameAndPhoneAndBirthDate(
        String name,
        String phone,
        LocalDate birthDate
    );

    Guest save(Guest guest);
    
    void delete(Guest guest);
} 
