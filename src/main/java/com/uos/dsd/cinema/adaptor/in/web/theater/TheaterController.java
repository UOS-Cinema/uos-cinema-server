package com.uos.dsd.cinema.adaptor.in.web.theater;

import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.application.port.in.theater.usecase.CreateTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.ReadTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.ModifyTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.DeleteTheaterUseCase;
import com.uos.dsd.cinema.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/theaters")
public class TheaterController {

    // TODO: admin 권한 검증

    private final CreateTheaterUseCase createTheaterUseCase;
    private final ReadTheaterUseCase readTheaterUseCase;
    private final ModifyTheaterUseCase modifyTheaterUseCase;
    private final DeleteTheaterUseCase deleteTheaterUseCase;

    @PostMapping
    public ApiResponse<Long> createTheater(@RequestBody TheaterCreateRequest request) {

        // TODO: admin 권한 검증

        Long theaterNumber = createTheaterUseCase.createTheater(request.toCommand());
        return ApiResponse.success(theaterNumber);
    }

    @GetMapping("/{theaterNumber}")
    public ApiResponse<TheaterResponse> getTheater(@PathVariable Long theaterNumber) {

        TheaterResponse theaterResponse = readTheaterUseCase.readTheater(theaterNumber);
        return ApiResponse.success(theaterResponse);
    }

    @PutMapping("/{theaterNumber}")
    public ApiResponse<Long> updateTheater(
            @PathVariable Long theaterNumber, 
            @RequestBody TheaterUpdateRequest request) {

        // TODO: admin 권한 검증

        Long modifiedTheaterNumber = modifyTheaterUseCase.modifyTheater(request.toCommand(theaterNumber));
        return ApiResponse.success(modifiedTheaterNumber);
    }

    @DeleteMapping("/{theaterNumber}")
    public ApiResponse<Void> deleteTheater(@PathVariable Long theaterNumber) {

        // TODO: admin 권한 검증

        deleteTheaterUseCase.deleteTheater(theaterNumber);
        return ApiResponse.success();
    }
}
