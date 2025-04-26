package com.uos.dsd.cinema.application.port.out.repository;

import java.util.Optional;

import com.uos.dsd.cinema.domain.model.user.Admin;

public interface AdminRepository {
    Optional<Admin> findByName(String name);
    Long save(Admin admin);
} 
