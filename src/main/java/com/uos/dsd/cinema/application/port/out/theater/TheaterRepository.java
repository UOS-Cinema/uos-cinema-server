package com.uos.dsd.cinema.application.port.out.theater;

import com.uos.dsd.cinema.domain.theater.Theater;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import java.util.List;
import java.util.Optional;

public interface TheaterRepository {

    Theater save(Theater theater);

    Theater saveAndFlush(Theater theater);

    List<Theater> findAll();

    Optional<Theater> findById(Long theaterNumber);

    List<List<LayoutElement>> getSeatingStatus(Long theaterNumber);

    void deleteById(Long theaterNumber);
}
