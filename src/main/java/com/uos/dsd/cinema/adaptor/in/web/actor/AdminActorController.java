package com.uos.dsd.cinema.adaptor.in.web.actor;

import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorUpdateRequest;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.adaptor.out.persistence.actor.ActorJpaRepository;
import com.uos.dsd.cinema.domain.actor.Actor;
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
@RequestMapping("/admin/actors")
@RequiredArgsConstructor
public class AdminActorController {

    private final ActorJpaRepository actorJpaRepository;

    @PostMapping
    public ApiResponse<Long> createActor(
        @UserRole Role role,
        @Valid @RequestBody ActorCreateRequest request) {
        
        if (role != Role.ADMIN) {
            throw new ForbiddenException("관리자 권한이 필요합니다.");
        }

        return ApiResponse.success(create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateActor(
            @PathVariable("id") Long id,
            @UserRole Role role,
            @Valid @RequestBody ActorUpdateRequest request) {
        
        if (role != Role.ADMIN) {
            throw new ForbiddenException("관리자 권한이 필요합니다.");
        }

        return ApiResponse.success(update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteActor(
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

        Actor actor = actorJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actor not found"));

        actor.delete();
        actorJpaRepository.save(actor);
    }

    @Transactional
    private Long update(Long id, ActorUpdateRequest request) {

        Actor actor = actorJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Actor not found"));

        actor.modifyPhotoUrl(request.photoUrl());
        return actorJpaRepository.save(actor).getId();
    }

    @Transactional
    private Long create(ActorCreateRequest request) {

        Actor actor = new Actor(request.name(), request.photoUrl());
        return actorJpaRepository.save(actor).getId();
    }
}
