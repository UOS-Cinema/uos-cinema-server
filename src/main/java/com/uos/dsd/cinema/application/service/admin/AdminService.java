package com.uos.dsd.cinema.application.service.admin;

import com.uos.dsd.cinema.application.port.in.admin.command.*;
import com.uos.dsd.cinema.application.port.in.admin.usecase.*;
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
public class AdminService implements
        AdminSignupUsecase,
        AdminLoginUsecase,
        AdminUpdateUsecase,
        AdminDeleteUsecase {

    private final AdminRepository adminRepository;

    @Override
    public Long signup(AdminSignupCommand command) {

        Admin admin = new Admin(command.username(), command.password());
        try {
            return adminRepository.save(admin).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Admin username already exists");
        }
    }

    @Override
    public Long login(AdminLoginCommand command) {

        Optional<Admin> admin = adminRepository.findByUsernameAndDeletedAtIsNull(command.username());
        if (admin.isEmpty() || !admin.get().isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin username or password");
        }
        return admin.get().getId();
    }

    @Override
    public void update(AdminUpdateCommand command) {
        
        Optional<Admin> admin = adminRepository.findByIdAndDeletedAtIsNull(command.id());
        if (admin.isEmpty() || !admin.get().isPasswordMatched(command.currentPassword())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin current password");
        }
        admin.get().updatePassword(command.newPassword());
    }

    @Override
    public void delete(AdminDeleteCommand command) {
        
        Optional<Admin> admin = adminRepository.findByIdAndDeletedAtIsNull(command.id());
        if (admin.isEmpty() || !admin.get().isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin id or password");
        }
        adminRepository.softDelete(admin.get());
    }
}
