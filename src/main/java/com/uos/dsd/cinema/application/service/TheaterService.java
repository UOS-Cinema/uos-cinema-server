package com.uos.dsd.cinema.application.service;

import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.application.port.in.theater.usecase.CreateTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.ReadTheaterUseCase;
import com.uos.dsd.cinema.application.port.out.theater.TheaterRepository;
import com.uos.dsd.cinema.application.port.in.theater.usecase.ModifyTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.usecase.DeleteTheaterUseCase;
import com.uos.dsd.cinema.application.port.in.theater.command.CreateTheaterCommand;
import com.uos.dsd.cinema.application.port.in.theater.command.ModifyTheaterCommand;
import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.theater.Theater;
import com.uos.dsd.cinema.domain.theater.exception.TheaterExceptionCode;
import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.NotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.hibernate.exception.ConstraintViolationException;

import jakarta.persistence.EntityManager;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class TheaterService implements
        CreateTheaterUseCase,
        ReadTheaterUseCase, 
        ModifyTheaterUseCase, 
        DeleteTheaterUseCase {

    private final EntityManager entityManager;
    private final TheaterRepository theaterRepository;

    @Override
    public Long createTheater(CreateTheaterCommand command) {

        Theater theater = new Theater(command.number(), command.name(), command.layout(),
                command.screenTypes());

        try {
            theaterRepository.save(theater);
            return theater.getNumber();
        } catch (ConstraintViolationException e) {
            throw new BadRequestException(TheaterExceptionCode.THEATER_ALREADY_EXISTS,
                    String.format(TheaterExceptionCode.THEATER_ALREADY_EXISTS.getMessage(),
                            command.number(), command.name()));
        }
    }
    
    @Override
    public List<TheaterResponse> readAllTheater() {
        return theaterRepository.findAll().stream()
            .map(theater -> new TheaterResponse(
                theater.getNumber(),
                theater.getName(),
                theater.getLayout(),
                theater.getScreenTypes().stream()
                    .map(ScreenType::getType)
                    .collect(Collectors.toList())
            ))
            .collect(Collectors.toList());
    }

    @Override
    public TheaterResponse readTheater(Long theaterNumber) {

        Theater theater = findByNumber(theaterNumber);

        return new TheaterResponse(
            theater.getNumber(),
            theater.getName(),
            theater.getLayout(),
            theater.getScreenTypes().stream()
                .map(ScreenType::getType)
                .collect(Collectors.toList())
        );
    }

    // TODO: 추후 수정 필요. 안됨
    @Override
    public Long modifyTheater(ModifyTheaterCommand command) {

        Theater theater = findByNumber(command.number());

        theater.modifyName(command.name());
        if (theater.getLayout() != command.layout() || theater.getScreenTypes() != command.screenTypes()) {
            if (theaterRepository.countByScreeningsFromNow(command.number()) > 0) {
                throw new BadRequestException(TheaterExceptionCode.THEATER_HAS_RUNNING_SCREENINGS);
            }
            deleteTheater(command.number());
            entityManager.flush();
            entityManager.detach(theater);
            theater = new Theater(command.number(), command.name(), command.layout(), command.screenTypes());
        }
        theaterRepository.save(theater);
        return theater.getNumber();
    }

    @Override
    public void deleteTheater(Long theaterNumber) {

        if (theaterRepository.countByScreeningsFromNow(theaterNumber) > 0) {
            throw new BadRequestException(TheaterExceptionCode.THEATER_HAS_RUNNING_SCREENINGS);
        }
        try {
            theaterRepository.deleteById(theaterNumber);
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(TheaterExceptionCode.THEATER_NOT_FOUND,
                    TheaterExceptionCode.THEATER_NOT_FOUND.getMessage() + ": " + theaterNumber);
        }
    }

    private Theater findByNumber(Long theaterNumber) {
        return theaterRepository.findById(theaterNumber)
                    .orElseThrow(() -> new NotFoundException(
                        TheaterExceptionCode.THEATER_NOT_FOUND, 
                        String.format(
                            TheaterExceptionCode.THEATER_NOT_FOUND.getMessage(), 
                            theaterNumber)));
    }
}
