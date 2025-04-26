package com.uos.dsd.cinema.application.port.out.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.uos.dsd.cinema.domain.model.user.Guest;

public interface GuestRepository {
    Optional<Guest> findById(Long id);
    List<Guest> findByNameAndPhoneAndBirthDate(String name, String phone, LocalDate birthDate);
    Long save(Guest guest);
} 
