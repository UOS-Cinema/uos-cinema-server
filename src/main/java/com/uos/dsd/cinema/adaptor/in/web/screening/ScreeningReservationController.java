package com.uos.dsd.cinema.adaptor.in.web.screening;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.application.port.in.screening.usecase.GetScreeningTheaterLayoutUseCase;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/screenings/{id}/reservations")
public class ScreeningReservationController {

    private final GetScreeningTheaterLayoutUseCase getScreeningTheaterLayoutUseCase;

    @GetMapping
    public ApiResponse<List<List<LayoutElement>>> getSeatingStatus(@PathVariable Long id) {

        return ApiResponse.success(
                getScreeningTheaterLayoutUseCase.getScreeningTheaterLayout(id));
    }
}