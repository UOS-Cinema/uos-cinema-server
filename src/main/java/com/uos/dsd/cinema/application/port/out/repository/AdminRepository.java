package com.uos.dsd.cinema.application.port.out.repository;

import com.uos.dsd.cinema.domain.model.Admin;

import java.util.Optional;

public interface AdminRepository {

    Optional<Admin> findByName(String name);

    Admin save(Admin admin);
}
