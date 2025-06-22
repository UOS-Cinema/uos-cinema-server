package com.uos.dsd.cinema.application.port.in.genre.command;

public record CreateGenreCommand(
    String name,
    String description,
    String imageUrl
) {
    
}
