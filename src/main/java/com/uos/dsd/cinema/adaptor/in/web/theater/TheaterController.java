package com.uos.dsd.cinema.adaptor.in.web.theater;

import java.util.List;

import lombok.RequiredArgsConstructor;

import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.application.port.in.theater.usecase.ReadTheaterUseCase;
import com.uos.dsd.cinema.common.response.ApiResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/theaters")
public class TheaterController {

    private final ReadTheaterUseCase readTheaterUseCase;

    @GetMapping
    public ApiResponse<List<TheaterResponse>> getTheaters() {

        List<TheaterResponse> theaters = readTheaterUseCase.readAllTheater();
        return ApiResponse.success(theaters);
    }

    @GetMapping("/{theaterNumber}")
    public ApiResponse<TheaterResponse> getTheater(@PathVariable Long theaterNumber) {

        TheaterResponse theaterResponse = readTheaterUseCase.readTheater(theaterNumber);
        return ApiResponse.success(theaterResponse);
    }
}
