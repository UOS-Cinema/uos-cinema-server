package com.uos.dsd.cinema.application.service.admin;

import com.uos.dsd.cinema.application.port.out.repository.admin.AdminRepository;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.domain.admin.Admin;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {

    private final AdminRepository adminRepository;

    public Long signupAdmin(String username, String password) {

        Admin admin = new Admin(username, password);
        try {
            return adminRepository.save(admin).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Admin username already exists");
        }
    }

    public Admin loginAdmin(String username, String password) {

        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isEmpty() || !admin.get().isPasswordMatched(password)) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin username or password");
        }
        return admin.get();
    }
}
