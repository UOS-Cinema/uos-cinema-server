package com.uos.dsd.cinema.adaptor.out.persistence.customer.guest;

import com.uos.dsd.cinema.application.port.out.customer.guest.GuestRepository;
import com.uos.dsd.cinema.domain.customer.guest.Guest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JpaGuestRepository extends CrudRepository<Guest, Long>, GuestRepository {

    List<Guest> findAllByNameAndPhoneAndBirthDate(
        String name,
        String phone,
        LocalDate birthDate
    );

    Optional<Guest> findById(Long id);

    Guest save(Guest guest);
    
    void delete(Guest guest);
} 
