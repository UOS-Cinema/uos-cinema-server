package com.uos.dsd.cinema.adaptor.in.web.hello;

import com.uos.dsd.cinema.common.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/hello")
@Slf4j
public class HelloController {

    @GetMapping
    public ApiResponse<String> hello() {
        log.info("hello api response");
        return ApiResponse.success("Hello, UOS Cinema!");
    }
}
