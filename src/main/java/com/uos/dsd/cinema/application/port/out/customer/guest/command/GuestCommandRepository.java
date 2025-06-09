package com.uos.dsd.cinema.application.port.out.customer.guest.command;

import java.time.LocalDate;
import java.util.List;

import com.uos.dsd.cinema.domain.customer.guest.Guest;

public interface GuestCommandRepository {
    
    List<Guest> findAllByNameAndPhoneAndBirthDate(
        String name,
        String phone,
        LocalDate birthDate
    );

    Guest save(Guest guest);
    
    void delete(Guest guest);
} 
