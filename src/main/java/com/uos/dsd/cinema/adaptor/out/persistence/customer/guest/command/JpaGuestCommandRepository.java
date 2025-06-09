package com.uos.dsd.cinema.adaptor.out.persistence.customer.guest.command;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.customer.guest.command.GuestCommandRepository;
import com.uos.dsd.cinema.domain.customer.guest.Guest;

@Repository
public interface JpaGuestCommandRepository extends CrudRepository<Guest, Long>, GuestCommandRepository {

    @Override
    List<Guest> findAllByNameAndPhoneAndBirthDate(String name, String phone, LocalDate birthDate);

    @Override
    Guest save(Guest guest);
    
    @Override
    void delete(Guest guest);
} 
