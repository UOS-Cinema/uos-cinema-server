package com.uos.dsd.cinema.domain.theater;

import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterCreateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.request.TheaterUpdateRequest;
import com.uos.dsd.cinema.adaptor.in.web.theater.response.TheaterResponse;
import com.uos.dsd.cinema.application.port.in.theater.command.CreateTheaterCommand;
import com.uos.dsd.cinema.application.port.in.theater.command.ModifyTheaterCommand;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TheaterFixture {

    private final static Long theaterNumber = 10L;
    private final static String theaterName = "Theater Name";
    private final static List<List<LayoutElement>> layout = Arrays.asList(
            Arrays.asList(LayoutElement.NONE, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.NONE),
            Arrays.asList(LayoutElement.NONE, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.NONE),
            Arrays.asList(LayoutElement.NONE, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.NONE),
            Arrays.asList(LayoutElement.NONE, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.NONE),
            Arrays.asList(LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.SEAT),
            Arrays.asList(LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.SEAT),
            Arrays.asList(LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.SEAT, 
            LayoutElement.AISLE, LayoutElement.SEAT, LayoutElement.SEAT, LayoutElement.SEAT)
        );
    private final static List<String> screenTypes = Arrays.asList("2D", "3D");

    private final static Theater theater = new Theater(
        theaterNumber,
        theaterName,
        layout,
        screenTypes.stream()
            .map(ScreenType::reference)
                    .collect(Collectors.toList()));
            
    private final static TheaterCreateRequest theaterCreateRequest = new TheaterCreateRequest(
        theaterNumber,
        theaterName,
        layout,
        screenTypes
    );

    private final static TheaterUpdateRequest theaterModifyRequest = new TheaterUpdateRequest(
        theaterName,
        layout,
        screenTypes
    );

    private final static CreateTheaterCommand createTheaterCommand = new CreateTheaterCommand(
        theaterNumber,
        theaterName,
        layout,
        screenTypes.stream()
            .map(ScreenType::reference)
            .collect(Collectors.toList())
    );

    private final static ModifyTheaterCommand modifyTheaterCommand = new ModifyTheaterCommand(
        theaterNumber,
        theaterName,
        layout,
        screenTypes.stream()
            .map(ScreenType::reference)
            .collect(Collectors.toList())
    );

    private final static TheaterResponse theaterResponse = new TheaterResponse(
        theaterNumber,
        theaterName,
        layout,
        screenTypes
    );

    public static Long getTheaterNumber() {
        return theaterNumber;
    }

    public static String getTheaterName() {
        return theaterName;
    }

    public static List<List<LayoutElement>> getLayout() {
        return layout;
    }

    public static List<String> getScreenTypes() {
        return screenTypes;
    }

    public static Theater getTheater() {
        return theater;
    }

    public static TheaterCreateRequest getTheaterCreateRequest() {
        return theaterCreateRequest;
    }
    
    public static TheaterUpdateRequest getTheaterModifyRequest() {
        return theaterModifyRequest;
    }

    public static CreateTheaterCommand getCreateTheaterCommand() {
        return createTheaterCommand;
    }
    
    public static ModifyTheaterCommand getModifyTheaterCommand() {
        return modifyTheaterCommand;
    }

    public static TheaterResponse getTheaterResponse() {
        return theaterResponse;
    }
}
