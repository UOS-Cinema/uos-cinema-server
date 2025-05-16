package com.uos.dsd.cinema.adaptor.out.persistence.jpa;

import com.uos.dsd.cinema.application.port.out.repository.admin.AdminRepository;
import com.uos.dsd.cinema.domain.admin.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAdminRepository extends JpaRepository<Admin, Long>, AdminRepository {

}
