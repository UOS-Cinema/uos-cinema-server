package com.uos.dsd.cinema.adaptor.in.web.genre.request;

import com.uos.dsd.cinema.application.port.in.genre.command.CreateGenreCommand;

public record CreateGenreRequest(
    String name,
    String description,
    String imageUrl
) {

    public CreateGenreCommand toCommand() {
        return new CreateGenreCommand(name, description, imageUrl);
    }
}
