package com.uos.dsd.cinema.application.port.out.customer_type;

import com.uos.dsd.cinema.domain.customer_type.CustomerType;

import java.util.List;

public interface CustomerTypeRepository {

    CustomerType save(CustomerType customerType);

    List<CustomerType> findAll();
}
