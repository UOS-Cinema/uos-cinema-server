package com.uos.dsd.cinema.adaptor.in.web.genre;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.application.port.out.genre.GenreRepository;
import com.uos.dsd.cinema.adaptor.in.web.genre.response.GenreResponse;
import com.uos.dsd.cinema.common.response.ApiResponse;


@RequiredArgsConstructor
@RestController
@RequestMapping("/genres")
public class GenreController {

    private final GenreRepository genreRepository;

    @GetMapping
    public ApiResponse<List<GenreResponse>> getGenres() {
        return ApiResponse.success(genreRepository.findAllGenreResponses());
    }
}
