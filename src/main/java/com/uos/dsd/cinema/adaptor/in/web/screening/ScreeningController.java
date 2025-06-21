package com.uos.dsd.cinema.adaptor.in.web.screening;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.application.port.in.screening.usecase.SearchScreeningUseCase;
import com.uos.dsd.cinema.application.port.in.screening.query.SearchScreeningQuery;
import com.uos.dsd.cinema.application.port.in.screening.usecase.GetScreeningDetailUseCase;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/screenings")
public class ScreeningController {

    private final SearchScreeningUseCase searchScreeningUseCase;
    private final GetScreeningDetailUseCase getScreeningDetailUseCase;

    @GetMapping("/{id}")
    public ApiResponse<ScreeningResponse> getScreening(@PathVariable Long id) {

        ScreeningResponse response = getScreeningDetailUseCase.get(id);
        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<List<ScreeningResponse>> getScreenings(
            @RequestParam(required = false) Long movieId, 
            @RequestParam(required = false) Long theaterId, 
            @RequestParam(required = false) LocalDate date) {

        if (date == null) {
            date = LocalDate.now();
        }
        List<ScreeningResponse> responses = searchScreeningUseCase.search(
            new SearchScreeningQuery(movieId, theaterId, date));
        return ApiResponse.success(responses);
    }
}
