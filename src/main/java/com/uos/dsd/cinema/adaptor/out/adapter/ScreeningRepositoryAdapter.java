package com.uos.dsd.cinema.adaptor.out.adapter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.uos.dsd.cinema.application.port.out.screening.ScreeningRepository;
import com.uos.dsd.cinema.application.port.out.screening.response.ScreeningResponse;
import com.uos.dsd.cinema.domain.screening.Screening;
import com.uos.dsd.cinema.adaptor.out.persistence.screening.ScreeningJpaRepository;

@Repository
@RequiredArgsConstructor
public class ScreeningRepositoryAdapter implements ScreeningRepository {

    private final ScreeningJpaRepository screeningJpaRepository;

    @Override
    public Screening save(Screening screening) {
        return screeningJpaRepository.save(screening);
    }

    @Override
    public Optional<Screening> findById(Long id) {
        return screeningJpaRepository.findById(id);
    }

    @Override
    public ScreeningResponse get(Long id) {
        return null;
    }

    @Override
    public List<ScreeningResponse> getAllWith(Long movieId, Long theaterId, Date date) {
        return null;
    }

    @Override
    public void delete(Long id) {
        screeningJpaRepository.deleteById(id);
    }
}
