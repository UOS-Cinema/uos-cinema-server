package com.uos.dsd.cinema.adaptor.in.web.director;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.response.ApiResponse.PageResponse;
import com.uos.dsd.cinema.adaptor.in.web.director.response.DirectorResponse;
import com.uos.dsd.cinema.domain.director.Director;
import com.uos.dsd.cinema.application.service.director.DirectorService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/directors")
public class DirectorController {
    // 배우 검색, 배우 리스트, 배우 상세 조회, 배우 영화

    private final DirectorService directorService;

    @GetMapping
    public ApiResponse<PageResponse<DirectorResponse>> getDirectorList(
        @RequestParam(required = false) String name,
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<Director> directors = directorService.getDirectorList(name, page, size);
        return ApiResponse.success(directors.map(DirectorResponse::of));
    }
}
