package com.uos.dsd.cinema.adaptor.in.web.theater;

import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/theaters")
public class TheaterController {

    // TODO: admin 권한 검증

    @PostMapping
    public ApiResponse<Long> createTheater(@RequestBody TheaterCreateRequest request) {

        Long theaterNumber = 0L;
        return ApiResponse.success(theaterNumber);
    }

    @GetMapping("/{theaterNumber}")
    public ApiResponse<TheaterResponse> getTheater(@PathVariable int theaterNumber) {

        TheaterResponse theaterResponse = null;
        return ApiResponse.success(theaterResponse);
    }

    @PutMapping("/{theaterNumber}")
    public ApiResponse<Integer> updateTheater(
            @PathVariable int theaterNumber, 
            @RequestBody TheaterUpdateRequest request) {

        return ApiResponse.success(theaterNumber);
    }

    @DeleteMapping("/{theaterNumber}")
    public ApiResponse<Void> deleteTheater(@PathVariable int theaterNumber) {

        return ApiResponse.success();
    }
}
