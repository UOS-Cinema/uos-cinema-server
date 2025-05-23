package com.uos.dsd.cinema.adaptor.in.web.theater.request;

import com.uos.dsd.cinema.application.port.in.theater.command.ModifyTheaterCommand;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

import java.util.List;

public record TheaterUpdateRequest(
    String name,
    List<List<LayoutElement>> layout,
    List<String> screenTypes
) {

    public ModifyTheaterCommand toCommand(Long theaterNumber) {

        return new ModifyTheaterCommand(
            theaterNumber,
            name,
            layout,
            screenTypes.stream()
                .map(ScreenType::reference)
                .toList());
    }
}
