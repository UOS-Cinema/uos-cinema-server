package com.uos.dsd.cinema.adaptor.out.persistence.jpa;

import com.uos.dsd.cinema.application.port.out.repository.admin.AdminRepository;
import com.uos.dsd.cinema.domain.admin.Admin;

import org.springframework.data.repository.CrudRepository;

public interface JpaAdminRepository extends CrudRepository<Admin, Long>, AdminRepository {

}
