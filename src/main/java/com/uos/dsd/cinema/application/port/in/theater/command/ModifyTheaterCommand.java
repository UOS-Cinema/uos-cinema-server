package com.uos.dsd.cinema.application.port.in.theater.command;

import java.util.List;

import com.uos.dsd.cinema.domain.screen_type.ScreenType;
import com.uos.dsd.cinema.domain.theater.enums.LayoutElement;

public record ModifyTheaterCommand(
    Long number,
    String name,
    List<List<LayoutElement>> layout,
    List<ScreenType> screenTypes
) {}