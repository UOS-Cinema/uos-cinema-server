package com.uos.dsd.cinema.application.service;

import com.uos.dsd.cinema.application.port.out.repository.AdminRepository;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.domain.model.Admin;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    public Long registerAdmin(String name, String password) {

        Admin admin = new Admin(name, password);
        try {
            return adminRepository.save(admin).getId();
        } catch (DataIntegrityViolationException e) {
            log.error("Failed to register admin: {}", e.getMessage());
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Admin name already exists");
        }
    }

    public Admin loginAdmin(String name, String password) {

        Optional<Admin> admin = adminRepository.findByName(name);
        if (admin.isEmpty() || !admin.get().isPasswordMatched(password)) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED);
        }
        return admin.get();
    }
}
