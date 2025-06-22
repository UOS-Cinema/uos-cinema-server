package com.uos.dsd.cinema.adaptor.in.web.actor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uos.dsd.cinema.application.service.actor.ActorService;
import com.uos.dsd.cinema.common.response.ApiResponse;
import com.uos.dsd.cinema.common.response.ApiResponse.PageResponse;
import com.uos.dsd.cinema.adaptor.in.web.actor.response.ActorResponse;
import com.uos.dsd.cinema.domain.actor.Actor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/actors")
public class ActorController {
    // 배우 검색, 배우 리스트, 배우 상세 조회, 배우 영화

    private final ActorService actorService;

    @GetMapping
    public ApiResponse<PageResponse<ActorResponse>> getActorList(
        @RequestParam(required = false) String name,
        @RequestParam(required = false, defaultValue = "0") Integer page,
        @RequestParam(required = false, defaultValue = "10") Integer size) {

        Page<Actor> actors = actorService.getActorList(name, page, size);
        return ApiResponse.success(actors.map(ActorResponse::of));
    }
}
