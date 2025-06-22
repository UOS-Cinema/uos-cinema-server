package com.uos.dsd.cinema.adaptor.in.web.genre.request;

import com.uos.dsd.cinema.application.port.in.genre.command.ModifyGenreCommand;

public record UpdateGenreRequest(
    String description,
    String imageUrl
) {

    public ModifyGenreCommand toCommand(String name) {
        return new ModifyGenreCommand(name, description, imageUrl);
    }
}
