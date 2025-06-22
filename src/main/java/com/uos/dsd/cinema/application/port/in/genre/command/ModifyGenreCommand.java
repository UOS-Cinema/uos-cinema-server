package com.uos.dsd.cinema.application.port.in.genre.command;

public record ModifyGenreCommand(
    String name,
    String description,
    String imageUrl
) {
    
}
