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
    private final static String updateTheaterName = "Update Theater Name";
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
    private final static List<List<LayoutElement>> updateLayout = Arrays.asList(
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
    private final static List<String> updateScreenTypes = Arrays.asList("2D", "3D", "4D");

    private final static Theater theater = new Theater(
        theaterNumber,
        theaterName,
        layout,
        screenTypes.stream()
            .map(ScreenType::reference)
            .collect(Collectors.toList())
    );

    private final static Theater updateTheater = new Theater(
        theaterNumber,
        updateTheaterName,
        updateLayout,
        updateScreenTypes.stream()
            .map(ScreenType::reference)
            .collect(Collectors.toList())
    );

    private final static TheaterCreateRequest theaterCreateRequest = new TheaterCreateRequest(
        theaterNumber,
        theaterName,
        layout,
        screenTypes
    );

    private final static TheaterUpdateRequest theaterModifyRequest = new TheaterUpdateRequest(
        updateTheaterName,
        updateLayout,
        updateScreenTypes
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
        updateTheaterName,
        updateLayout,
        updateScreenTypes.stream()
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

    public static String getUpdateTheaterName() {
        return updateTheaterName;
    }

    public static List<List<LayoutElement>> getLayout() {
        return layout;
    }

    public static List<List<LayoutElement>> getUpdateLayout() {
        return updateLayout;
    }

    public static List<String> getScreenTypes() {
        return screenTypes;
    }

    public static List<String> getUpdateScreenTypes() {
        return updateScreenTypes;
    }

    public static Theater getTheater() {
        return theater;
    }

    public static Theater getUpdateTheater() {
        return updateTheater;
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
