package com.uos.dsd.cinema.application.port.out.theater;

import com.uos.dsd.cinema.domain.theater.Theater;

import java.util.Optional;

public interface TheaterRepository {

    Theater save(Theater theater);

    Theater saveAndFlush(Theater theater);

    Optional<Theater> findById(Long theaterNumber);

    void deleteById(Long theaterNumber);
}
