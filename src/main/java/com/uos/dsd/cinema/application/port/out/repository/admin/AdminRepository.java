package com.uos.dsd.cinema.application.port.out.repository.admin;

import com.uos.dsd.cinema.domain.admin.Admin;

import java.util.Optional;

public interface AdminRepository {

    Optional<Admin> findByUsernameAndDeletedAtIsNull(String username);

    Optional<Admin> findByIdAndDeletedAtIsNull(Long id);

    Admin save(Admin admin);

    void softDelete(Admin admin);
}
