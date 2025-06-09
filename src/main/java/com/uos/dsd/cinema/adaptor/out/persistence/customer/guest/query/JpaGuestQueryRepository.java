package com.uos.dsd.cinema.adaptor.out.persistence.customer.guest.query;

import com.uos.dsd.cinema.application.port.out.customer.guest.query.GuestQueryRepository;
import com.uos.dsd.cinema.domain.customer.guest.Guest;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaGuestQueryRepository extends CrudRepository<Guest, Long>, GuestQueryRepository {

    @Override
    Optional<Guest> findByCustomerId(Long customerId);
}
