package com.uos.dsd.cinema.application.port.out.repository;

import com.uos.dsd.cinema.domain.model.user.Customer.UserType;

public interface CustomerRepository {
  Long save(UserType userType);
}
