package com.uos.dsd.cinema.adaptor.out.persistence.theater.adaptor;

import com.uos.dsd.cinema.adaptor.out.persistence.theater.jpa.TheaterJPARepository;
import com.uos.dsd.cinema.application.port.out.theater.TheaterRepository;
import com.uos.dsd.cinema.domain.theater.Theater;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;
import com.uos.dsd.cinema.domain.theater.exception.TheaterExceptionCode;

import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TheaterRepositoryAdaptor implements TheaterRepository {

    private final TheaterJPARepository theaterJPARepository;

    @Override
    public Theater save(Theater theater) {
        return theaterJPARepository.save(theater);
    }

    @Override
    public Theater saveAndFlush(Theater theater) {
        return theaterJPARepository.saveAndFlush(theater);
    }

    @Override
    public void deleteById(Long theaterNumber) {
        theaterJPARepository.deleteById(theaterNumber);
    }

    @Override
    public List<Theater> findAll() {
        return theaterJPARepository.findAll();
    }

    @Override
    public Optional<Theater> findById(Long theaterNumber) {
        return theaterJPARepository.findById(theaterNumber);
    }

    @Override
    public List<List<LayoutElement>> getSeatingStatus(Long theaterNumber) {
        Theater theater = theaterJPARepository.findById(theaterNumber)
                .orElseThrow(() -> new NotFoundException(TheaterExceptionCode.THEATER_NOT_FOUND));
        return theater.getLayout();
    }
}
