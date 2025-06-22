package com.uos.dsd.cinema.adaptor.in.web.screening;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.common.response.ApiResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/screenings/{id}/reservations")
public class ScreeningReservationController {

    private final ScreeningRepository screeningRepository;

    @GetMapping
    public ApiResponse<List<List<LayoutElement>>> getSeatingStatus(@PathVariable Long id) {

        return ApiResponse.success(screeningRepository.getSeatingStatus(id));
    }
}