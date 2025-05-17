package com.uos.dsd.cinema.application.port.in.theater.command;

import java.util.List;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

public record CreateTheaterCommand(
    Long number,
    String name,
    List<List<Integer>> layout,
    List<ScreenType> screenTypes
) {}