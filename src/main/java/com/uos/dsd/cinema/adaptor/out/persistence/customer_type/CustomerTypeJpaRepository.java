package com.uos.dsd.cinema.adaptor.out.persistence.customer_type;

import com.uos.dsd.cinema.application.port.out.customer_type.CustomerTypeRepository;
import com.uos.dsd.cinema.domain.customer_type.CustomerType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerTypeJpaRepository 
        extends JpaRepository<CustomerType, String>, CustomerTypeRepository {
}
