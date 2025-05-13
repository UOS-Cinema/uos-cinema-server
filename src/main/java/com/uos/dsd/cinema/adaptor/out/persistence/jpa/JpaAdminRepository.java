package com.uos.dsd.cinema.adaptor.out.persistence.jpa;

import com.uos.dsd.cinema.application.port.out.repository.AdminRepository;
import com.uos.dsd.cinema.domain.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAdminRepository extends JpaRepository<Admin, Long>, AdminRepository {

}
