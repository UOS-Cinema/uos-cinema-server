package com.uos.dsd.cinema.adaptor.out.persistence.admin.jpa;

import com.uos.dsd.cinema.application.port.out.repository.admin.AdminRepository;
import com.uos.dsd.cinema.domain.admin.Admin;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface JpaAdminRepository extends CrudRepository<Admin, Long>, AdminRepository {

    Optional<Admin> findByUsernameAndDeletedAtIsNull(String username);

    Optional<Admin> findByIdAndDeletedAtIsNull(Long id);

    Admin save(Admin admin);

    default void softDelete(Admin admin) {

        admin.delete();
        save(admin);
    }
}
