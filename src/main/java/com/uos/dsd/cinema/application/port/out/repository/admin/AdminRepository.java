package com.uos.dsd.cinema.application.port.out.repository.admin;

import com.uos.dsd.cinema.domain.admin.Admin;

import java.util.Optional;

public interface AdminRepository {

    Optional<Admin> findByUsername(String username);

    Optional<Admin> findById(Long id);

    Admin save(Admin admin);

    void delete(Admin admin);
}
