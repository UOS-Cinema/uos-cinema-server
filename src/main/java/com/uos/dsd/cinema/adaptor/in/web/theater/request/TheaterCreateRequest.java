package com.uos.dsd.cinema.adaptor.in.web.theater.request;

import com.uos.dsd.cinema.application.port.in.theater.command.CreateTheaterCommand;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import java.util.List;

public record TheaterCreateRequest(
    Long number,
    String name,
    List<List<LayoutElement>> layout,
    List<String> screenTypes
) {

    public CreateTheaterCommand toCommand() {

        return new CreateTheaterCommand(
            number, 
            name, 
            layout, 
            screenTypes.stream()
                .map(ScreenType::reference)
                .toList());
    }
}
