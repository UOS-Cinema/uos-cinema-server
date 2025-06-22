package com.uos.dsd.cinema.adaptor.in.web.screen_type;

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

import com.uos.dsd.cinema.adaptor.in.web.screen_type.request.CreateScreenTypeRequest;
import com.uos.dsd.cinema.adaptor.in.web.screen_type.request.UpdateScreenTypeRequest;
import com.uos.dsd.cinema.adaptor.out.persistence.screen_type.ScreenTypeJpaRepository;
import com.uos.dsd.cinema.application.registry.ScreenTypeRegistry;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/screen-types")
public class AdminScreenTypeController {

    private final ScreenTypeJpaRepository screenTypeJpaRepository;
    private final ScreenTypeRegistry screenTypeRegistry;

    @PostMapping
    public ApiResponse<ScreenType> createScreenType(
        @UserRole Role role,
            @RequestBody CreateScreenTypeRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(createScreenType(request));
    }

    @GetMapping
    public ApiResponse<List<ScreenType>> getAllScreenTypes(
            @UserRole Role role) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(screenTypeRegistry.getAll());
    }

    @PutMapping("/{name}")
    public ApiResponse<ScreenType> updateScreenType(
        @UserRole Role role,
        @PathVariable String name,
        @RequestBody UpdateScreenTypeRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(updateScreenType(name, request));
    }

    @DeleteMapping("/{name}")
    public ApiResponse<Void> deleteScreenType(
        @UserRole Role role,
        @PathVariable String name) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        deleteScreenType(name);
        return ApiResponse.success();
    }

    @Transactional
    private ScreenType updateScreenType(String name, UpdateScreenTypeRequest request) {
        ScreenType screenType = screenTypeRegistry.get(name);
        if (screenType == null) {
            throw new NotFoundException();
        }
        screenType.modifyIconUrl(request.iconUrl());
        screenType.modifyPrice(request.price());
        screenTypeJpaRepository.save(screenType);
        screenTypeRegistry.reload(null);
        return screenType;
    }

    @Transactional
    private ScreenType createScreenType(CreateScreenTypeRequest request) {
        ScreenType screenType =
                new ScreenType(request.type(), request.iconUrl(), request.price());
        screenTypeJpaRepository.save(screenType);
        screenTypeRegistry.reload(null);
        return screenType;
    }

    // Screen Type이 사용중인 극장이나 영화가 있으면 삭제 불가능
    @Transactional
    private void deleteScreenType(String name) {
        ScreenType screenType = screenTypeRegistry.get(name);
        if (screenType == null) {
            throw new NotFoundException();
        }
        if (screenTypeJpaRepository.countFromTheater(name) > 0) {
            throw new BadRequestException("Screen type is used in theater");
        }
        if (screenTypeJpaRepository.countFromMovie(name) > 0) {
            throw new BadRequestException("Screen type is used in movie");
        }
        screenTypeJpaRepository.delete(screenType);
        screenTypeRegistry.reload(null);
    }
}
