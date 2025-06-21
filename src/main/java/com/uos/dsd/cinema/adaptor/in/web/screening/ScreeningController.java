package com.uos.dsd.cinema.adaptor.in.web.screening;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.core.annotation.UserRole;
import com.uos.dsd.cinema.core.security.SecurityConstants.Role;
import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.screening.request.ScreeningUpdateRequest;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningCreateUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningModifyUseCase;
import com.uos.dsd.cinema.application.port.in.screening.usecase.ScreeningDeleteUseCase;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.domain.screening.exception.ScreeningExceptionCode;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    private final ScreeningCreateUseCase screeningCreateUseCase;
    private final ScreeningModifyUseCase screeningModifyUseCase;
    private final ScreeningDeleteUseCase screeningDeleteUseCase;
    private final ScreeningRepository screeningRepository;

    @PostMapping
    public ApiResponse<Long> createScreening(
            @UserRole Role role,
            @RequestBody ScreeningCreateRequest request) {

        if (Role.ADMIN != role) {
            throw new ForbiddenException(ScreeningExceptionCode.ONLY_ADMIN_CAN_ACCESS);
        }
        Long id = screeningCreateUseCase.create(request.toCommand());
        return ApiResponse.success(id);
    }

    @GetMapping("/{id}")
    public ApiResponse<ScreeningResponse> getScreening(@PathVariable Long id) {

        ScreeningResponse response = screeningRepository.get(id);
        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<List<ScreeningResponse>> getScreenings(
            @RequestParam(required = false) Long movieId, 
            @RequestParam(required = false) Long theaterId, 
            @RequestParam(required = false) Date date) {

        if (date == null) {
            date = new Date();
        }
        List<ScreeningResponse> responses = screeningRepository.getAllWith(movieId, theaterId, date);
        return ApiResponse.success(responses);
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updateScreening(
            @UserRole Role role,
            @PathVariable Long id,
            @RequestBody ScreeningUpdateRequest request) {

        if (Role.ADMIN != role) {
            throw new ForbiddenException(ScreeningExceptionCode.ONLY_ADMIN_CAN_ACCESS);
        }
        screeningModifyUseCase.modify(request.toCommand(id));
        return ApiResponse.success(id);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> deleteScreening(
            @UserRole Role role,
            @PathVariable Long id) {

        if (Role.ADMIN != role) {
            throw new ForbiddenException(ScreeningExceptionCode.ONLY_ADMIN_CAN_ACCESS);
        }
        screeningDeleteUseCase.delete(id);
        return ApiResponse.success(id);
    }
}
