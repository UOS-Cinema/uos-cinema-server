package com.uos.dsd.cinema.adaptor.out.persistence;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.repository.CustomerRepository;
import com.uos.dsd.cinema.domain.model.user.Customer;
import com.uos.dsd.cinema.domain.model.user.Customer.UserType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaCustomerRepository implements CustomerRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long save(UserType userType) {
        Customer customer = Customer.builder()
            .userType(userType)
            .build();
        entityManager.persist(customer);
        return customer.getId();
    }
}
