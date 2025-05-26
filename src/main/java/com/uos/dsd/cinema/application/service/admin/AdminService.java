package com.uos.dsd.cinema.application.service.admin;

import com.uos.dsd.cinema.application.port.in.admin.command.*;
import com.uos.dsd.cinema.application.port.in.admin.usecase.*;
import com.uos.dsd.cinema.application.port.out.repository.admin.AdminRepository;
import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.domain.admin.model.Admin;
import com.uos.dsd.cinema.core.security.CustomUserDetails;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import jakarta.transaction.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminService implements
        SignupAdminUsecase,
        LoginAdminUsecase,
        UpdateAdminUsecase,
        DeleteAdminUsecase {

    private final AdminRepository adminRepository;

    @Override
    public Long signup(SignupAdminCommand command) {

        Admin admin = new Admin(command.username(), command.password());
        try {
            return adminRepository.save(admin).getId();
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException(CommonResultCode.BAD_REQUEST, "Admin username already exists");
        }
    }

    @Override
    public Long login(LoginAdminCommand command) {

        Optional<Admin> admin = adminRepository.findByUsernameAndDeletedAtIsNull(command.username());
        if (admin.isEmpty() || !admin.get().isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin username or password");
        }
        return admin.get().getId();
    }

    @Override
    public void update(UpdateAdminCommand command) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long requesterId = userDetails.getId();
        if (!requesterId.equals(command.id())) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only update your own account");
        }
        
        Optional<Admin> admin = adminRepository.findByIdAndDeletedAtIsNull(command.id());
        if (admin.isEmpty() || !admin.get().isPasswordMatched(command.currentPassword())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin current password");
        }
        admin.get().updatePassword(command.newPassword());
    }

    @Override
    public void delete(DeleteAdminCommand command) {

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long requesterId = userDetails.getId();
        if (!requesterId.equals(command.id())) {
            throw new ForbiddenException(CommonResultCode.FORBIDDEN, "You can only delete your own account");
        }
        
        Optional<Admin> admin = adminRepository.findByIdAndDeletedAtIsNull(command.id());
        if (admin.isEmpty() || !admin.get().isPasswordMatched(command.password())) {
            throw new UnauthorizedException(CommonResultCode.UNAUTHORIZED, "Invalid admin id or password");
        }
        adminRepository.softDelete(admin.get());
    }
}
