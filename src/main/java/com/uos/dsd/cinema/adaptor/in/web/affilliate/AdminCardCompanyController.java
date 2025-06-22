package com.uos.dsd.cinema.adaptor.in.web.affilliate;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.adaptor.in.web.affilliate.request.CreateCardCompanyRequest;
import com.uos.dsd.cinema.adaptor.in.web.affilliate.request.UpdateCardCompanyRequest;
import com.uos.dsd.cinema.adaptor.out.persistence.affliliate.CardCompanyJpaRepository;
import com.uos.dsd.cinema.application.registry.CardCompanyRegistry;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.affiliate.CardCompany;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/card-companies")
public class AdminCardCompanyController {

    private final CardCompanyJpaRepository cardCompanyJpaRepository;
    private final CardCompanyRegistry cardCompanyRegistry;

    @PostMapping
    public ApiResponse<CardCompany> createCardCompany(
        @UserRole Role role,
            @RequestBody CreateCardCompanyRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(createCardCompany(request));
    }

    @GetMapping
    public ApiResponse<List<CardCompany>> getAllCardCompanies(
            @UserRole Role role) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(cardCompanyRegistry.getAll());
    }
    
    @PutMapping("/{name}")
    public ApiResponse<CardCompany> updateCardCompany(
        @UserRole Role role,
        @PathVariable String name,
            @RequestBody UpdateCardCompanyRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(updateCardCompany(name, request));
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deleteCardCompany(
        @UserRole Role role,
        @PathVariable String name) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        deleteCardCompany(name);
        return ApiResponse.success();
    }

    @Transactional
    private CardCompany updateCardCompany(String name, UpdateCardCompanyRequest request) {
        CardCompany cardCompany = cardCompanyRegistry.get(name);
        if (cardCompany == null) {
            throw new NotFoundException();
        }
        cardCompany.modifyLogoUrl(request.logoUrl());
        cardCompany.modifyDiscountAmount(request.discountAmount());
        cardCompanyJpaRepository.save(cardCompany);
        cardCompanyRegistry.reload(null);
        return cardCompany;
    }

    @Transactional
    private CardCompany createCardCompany(CreateCardCompanyRequest request) {
        CardCompany cardCompany =
                new CardCompany(request.name(), request.logoUrl(), request.discountAmount());
        cardCompanyJpaRepository.save(cardCompany);
        cardCompanyRegistry.reload(null);
        return cardCompany;
    }

    @Transactional
    private void deleteCardCompany(String name) {
        CardCompany cardCompany = cardCompanyRegistry.get(name);
        if (cardCompany == null) {
            throw new NotFoundException();
        }
        cardCompanyJpaRepository.delete(cardCompany);
        cardCompanyRegistry.reload(null);
    }
}
