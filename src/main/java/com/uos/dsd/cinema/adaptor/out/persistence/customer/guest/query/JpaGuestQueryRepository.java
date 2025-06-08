package com.uos.dsd.cinema.adaptor.out.persistence.customer.guest.query;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.customer.guest.query.GuestQueryRepository;
import com.uos.dsd.cinema.domain.customer.guest.command.Guest;
import com.uos.dsd.cinema.domain.customer.guest.query.GuestInfo;

@Repository
public interface JpaGuestQueryRepository extends CrudRepository<Guest, Long>, GuestQueryRepository {
    
    @Override
    @Query(value = "SELECT name, phone, birth_date AS birthDate FROM guests WHERE customer_id = :customerId", nativeQuery = true)
    Optional<GuestInfo> findByCustomerId(@Param("customerId") Long customerId);
}
