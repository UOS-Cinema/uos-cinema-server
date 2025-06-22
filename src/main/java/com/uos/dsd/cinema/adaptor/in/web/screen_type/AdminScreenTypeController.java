package com.uos.dsd.cinema.adaptor.in.web.screen_type;

import com.uos.dsd.cinema.adaptor.in.web.screen_type.request.CreateScreenTypeRequest;
import com.uos.dsd.cinema.adaptor.in.web.screen_type.request.UpdateScreenTypeRequest;
import com.uos.dsd.cinema.adaptor.out.persistence.screen_type.ScreenTypeJpaRepository;
import com.uos.dsd.cinema.application.registry.ScreenTypeRegistry;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

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
@RequestMapping("/admin/screen-types")
public class AdminScreenTypeController {

    private final ScreenTypeJpaRepository screenTypeJpaRepository;
    private final ScreenTypeRegistry screenTypeRegistry;

    @PostMapping
    public ApiResponse<String> createScreenType(
        @UserRole Role role,
            @RequestBody CreateScreenTypeRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(createScreenType(request).getType());
    }

    @GetMapping
    public ApiResponse<List<ScreenType>> getAllScreenTypes() {
        return ApiResponse.success(screenTypeRegistry.getAll());
    }

    @PutMapping("/{type}")
    public ApiResponse<String> updateScreenType(
        @UserRole Role role,
        @PathVariable String type,
        @RequestBody UpdateScreenTypeRequest request) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        return ApiResponse.success(updateScreenType(type, request).getType());
    }

    @DeleteMapping("/{type}")
    public ApiResponse<Void> deleteScreenType(
        @UserRole Role role,
        @PathVariable String type) {

        if (role != Role.ADMIN) {
            throw new ForbiddenException();
        }

        deleteScreenType(type);
        return ApiResponse.success();
    }

    @Transactional
    private ScreenType updateScreenType(String type, UpdateScreenTypeRequest request) {
        ScreenType screenType = screenTypeRegistry.get(type);
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
    private void deleteScreenType(String type) {
        ScreenType screenType = screenTypeRegistry.get(type);
        if (screenType == null) {
            throw new NotFoundException();
        }
        if (screenTypeJpaRepository.countFromTheater(type) > 0) {
            throw new BadRequestException("Screen type is used in theater");
        }
        if (screenTypeJpaRepository.countFromMovie(type) > 0) {
            throw new BadRequestException("Screen type is used in movie");
        }
        screenTypeJpaRepository.delete(screenType);
        screenTypeRegistry.reload(null);
    }
}
