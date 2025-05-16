package com.uos.dsd.cinema.application.port.out.repository.admin;

import com.uos.dsd.cinema.domain.admin.Admin;

import java.util.Optional;

public interface AdminRepository {

    Optional<Admin> findByName(String name);

    Admin save(Admin admin);
}
