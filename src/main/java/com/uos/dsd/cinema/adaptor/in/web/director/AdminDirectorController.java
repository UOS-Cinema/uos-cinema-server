package com.uos.dsd.cinema.adaptor.in.web.director;

import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.director.request.DirectorUpdateRequest;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.adaptor.out.persistence.director.DirectorJpaRepository;
import com.uos.dsd.cinema.domain.director.Director;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin/directors")
@RequiredArgsConstructor
public class AdminDirectorController {

    private final DirectorJpaRepository directorJpaRepository;

    @PostMapping
    public ApiResponse<Long> createDirector(
        @UserRole Role role,
        @Valid @RequestBody DirectorCreateRequest request) {
        
        if (role != Role.ADMIN) {
            throw new ForbiddenException("관리자 권한이 필요합니다.");
        }

        return ApiResponse.success(create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateDirector(
            @PathVariable("id") Long id,
            @UserRole Role role,
            @Valid @RequestBody DirectorUpdateRequest request) {
        
        if (role != Role.ADMIN) {
            throw new ForbiddenException("관리자 권한이 필요합니다.");
        }

        return ApiResponse.success(update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteDirector(
            @PathVariable("id") Long id,
            @UserRole Role role) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException("관리자 권한이 필요합니다.");
        }

        delete(id);
        return ApiResponse.success();
    }

    @Transactional
    private void delete(Long id) {

        Director director = directorJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director not found"));

        director.delete();
        directorJpaRepository.save(director);
    }

    @Transactional
    private Long update(Long id, DirectorUpdateRequest request) {

        Director director = directorJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Director not found"));

        director.modifyPhotoUrl(request.photoUrl());
        return directorJpaRepository.save(director).getId();
    }

    @Transactional
    private Long create(DirectorCreateRequest request) {

        Director director = new Director(request.name(), request.photoUrl());
        return directorJpaRepository.save(director).getId();
    }
}
