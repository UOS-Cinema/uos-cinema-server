package com.uos.dsd.cinema.adaptor.in.web.theater;

import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;
import com.uos.dsd.cinema.application.port.in.theater.usecase.CreateTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.ModifyTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.DeleteTheaterUseCase;
import com.uos.dsd.cinema.domain.theater.exception.TheaterExceptionCode;
import com.uos.dsd.cinema.common.exception.http.UnauthorizedException;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin/theaters")
public class AdminTheaterController {

    private final CreateTheaterUseCase createTheaterUseCase;
    private final ModifyTheaterUseCase modifyTheaterUseCase;
    private final DeleteTheaterUseCase deleteTheaterUseCase;

    @PostMapping
    public ApiResponse<Long> createTheater(
        @UserRole Role role,
        @RequestBody TheaterCreateRequest request) {

        if (role != Role.ADMIN) {
            throw new UnauthorizedException(TheaterExceptionCode.ONLY_ADMIN_CAN_ACCESS);
        }

        Long theaterNumber = createTheaterUseCase.createTheater(request.toCommand());
        return ApiResponse.success(theaterNumber);
    }

    @PutMapping("/{theaterNumber}")
    public ApiResponse<Long> updateTheater(
            @UserRole Role role,
            @PathVariable Long theaterNumber, 
            @RequestBody TheaterUpdateRequest request) {

        if (role != Role.ADMIN) {
            throw new UnauthorizedException(TheaterExceptionCode.ONLY_ADMIN_CAN_ACCESS);
        }

        Long modifiedTheaterNumber = modifyTheaterUseCase.modifyTheater(request.toCommand(theaterNumber));
        return ApiResponse.success(modifiedTheaterNumber);
    }

    @DeleteMapping("/{theaterNumber}")
    public ApiResponse<Void> deleteTheater(
            @UserRole Role role,
            @PathVariable Long theaterNumber) {

        if (role != Role.ADMIN) {
            throw new UnauthorizedException(TheaterExceptionCode.ONLY_ADMIN_CAN_ACCESS);
        }

        deleteTheaterUseCase.deleteTheater(theaterNumber);
        return ApiResponse.success();
    }
}
