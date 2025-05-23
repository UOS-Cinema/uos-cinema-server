package com.uos.dsd.cinema.application.port.out.repository.admin;

import java.util.Optional;

import com.uos.dsd.cinema.domain.admin.model.Admin;

public interface AdminRepository {

    Optional<Admin> findByUsernameAndDeletedAtIsNull(String username);

    Optional<Admin> findByIdAndDeletedAtIsNull(Long id);

    Admin save(Admin admin);

    void softDelete(Admin admin);
}
