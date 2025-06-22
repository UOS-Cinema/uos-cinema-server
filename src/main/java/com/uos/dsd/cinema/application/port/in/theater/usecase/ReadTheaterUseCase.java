package com.uos.dsd.cinema.application.port.in.theater.usecase;

import java.util.List;

import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;

public interface ReadTheaterUseCase {

    TheaterResponse readTheater(Long theaterNumber);

    List<TheaterResponse> readAllTheater();
}