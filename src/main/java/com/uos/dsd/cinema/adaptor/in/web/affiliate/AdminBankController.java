package com.uos.dsd.cinema.adaptor.in.web.affiliate;

import com.uos.dsd.cinema.adaptor.in.web.affiliate.request.CreateBankRequest;
import com.uos.dsd.cinema.adaptor.in.web.affiliate.request.UpdateBankRequest;
import com.uos.dsd.cinema.adaptor.out.persistence.affliliate.BankJpaRepository;
import com.uos.dsd.cinema.application.registry.BankRegistry;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.domain.affiliate.Bank;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/banks")
public class AdminBankController {

    private final BankJpaRepository bankJpaRepository;
    private final BankRegistry bankRegistry;

    @PostMapping
    public ApiResponse<String> createBank(
        @UserRole Role role,
            @RequestBody CreateBankRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(createBank(request).getName());
    }

    @GetMapping
    public ApiResponse<List<Bank>> getAllBanks() {
        return ApiResponse.success(bankRegistry.getAll());
    }
    
    @PutMapping("/{name}")
    public ApiResponse<String> updateBank(
        @UserRole Role role,
        @PathVariable String name,
            @RequestBody UpdateBankRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(updateBank(name, request).getName());
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deleteBank(
        @UserRole Role role,
        @PathVariable String name) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        deleteCardCompany(name);
        return ApiResponse.success();
    }

    @Transactional
    private Bank updateBank(String name, UpdateBankRequest request) {
        Bank bank = bankRegistry.get(name);
        if (bank == null) {
            throw new NotFoundException();
        }
        bank.modifyLogoUrl(request.logoUrl());
        bank.modifyDiscountAmount(request.discountAmount());
        bankJpaRepository.save(bank);
        bankRegistry.reload(null);
        return bank;
    }

    @Transactional
    private Bank createBank(CreateBankRequest request) {
        Bank bank =
                new Bank(request.name(), request.logoUrl(), request.discountAmount());
        bankJpaRepository.save(bank);
        bankRegistry.reload(null);
        return bank;
    }

    @Transactional
    private void deleteCardCompany(String name) {
        Bank bank = bankRegistry.get(name);
        if (bank == null) {
            throw new NotFoundException();
        }
        bankJpaRepository.delete(bank);
        bankRegistry.reload(null);
    }
}
