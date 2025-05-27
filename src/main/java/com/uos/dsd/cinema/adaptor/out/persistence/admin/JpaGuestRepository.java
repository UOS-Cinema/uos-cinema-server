package com.uos.dsd.cinema.adaptor.out.persistence.jpa;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.repository.guest.GuestRepository;
import com.uos.dsd.cinema.domain.guest.Guest;

@Repository
public interface JpaGuestRepository extends JpaRepository<Guest, Long>, GuestRepository {

    List<Guest> findAllByNameAndPhoneAndBirthDate(
        String name,
        String phone,
        LocalDate birthDate
    );

    Optional<Guest> findById(Long id);

    Guest save(Guest guest);
    
    void delete(Guest guest);
} 
