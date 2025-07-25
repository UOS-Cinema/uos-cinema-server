package com.uos.dsd.cinema.application.service.theater;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.application.port.in.theater.command.CreateTheaterCommand;
import com.uos.dsd.cinema.application.port.in.theater.command.ModifyTheaterCommand;
import com.uos.dsd.cinema.application.service.TheaterService;
import com.uos.dsd.cinema.application.port.out.theater.TheaterRepository;
import com.uos.dsd.cinema.domain.theater.Theater;
import com.uos.dsd.cinema.domain.theater.TheaterFixture;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;


import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class TheaterServiceTest {

    @InjectMocks
    private TheaterService theaterService;

    @Mock
    private TheaterRepository theaterRepository;

    private final Long theaterNumber = TheaterFixture.getTheaterNumber();
    private final String theaterName = TheaterFixture.getTheaterName();
    private final List<List<LayoutElement>> layout = TheaterFixture.getLayout();
    private final List<String> screenTypes = TheaterFixture.getScreenTypes();
    private final Theater theater = TheaterFixture.getTheater();
    private final Theater updateTheater = TheaterFixture.getUpdateTheater();
    private final CreateTheaterCommand createTheaterCommand = TheaterFixture.getCreateTheaterCommand();
    private final ModifyTheaterCommand modifyTheaterCommand = TheaterFixture.getModifyTheaterCommand();

    @Test
    public void testCreateTheater() {

        // given
        when(theaterRepository.save(any(Theater.class)))
                .thenReturn(theater);

        // when
        Long createdTheaterNumber = theaterService.createTheater(createTheaterCommand);
        
        // then
        assertEquals(theaterNumber, createdTheaterNumber);
    }

    @Test
    public void testModifyTheater() {

        // given
        when(theaterRepository.findById(theaterNumber))
                .thenReturn(Optional.of(theater));
        when(theaterRepository.save(any(Theater.class)))
                .thenReturn(updateTheater);
        when(theaterRepository.saveAndFlush(any(Theater.class)))
                .thenReturn(theater);

        // when
        Long modifiedTheaterNumber = theaterService.modifyTheater(modifyTheaterCommand);

        // then
        assertEquals(theaterNumber, modifiedTheaterNumber);
    }

    @Test
    public void testDeleteTheater() {

        // given
        doNothing().when(theaterRepository).deleteById(theaterNumber);

        // when
        theaterService.deleteTheater(theaterNumber);

        // then
        verify(theaterRepository, times(1)).deleteById(theaterNumber);
    }

    @Test
    public void testReadTheater() {

        // given
        when(theaterRepository.findById(theaterNumber))
                .thenReturn(Optional.of(theater));

        // when
        TheaterResponse foundTheater = theaterService.readTheater(theaterNumber);

        // then
        assertEquals(theaterNumber, foundTheater.number());
        assertEquals(theaterName, foundTheater.name());
        assertEquals(layout, foundTheater.layout());
        assertEquals(screenTypes, foundTheater.screenTypes());
    }
}