package com.uos.dsd.cinema.adaptor.in.web.movie.response;

import com.uos.dsd.cinema.domain.genre.Genre;
import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.domain.movie.enums.CastingType;
import com.uos.dsd.cinema.domain.movie.enums.MovieRating;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public record MovieResponse(
    Long id,
    String title,
    String synopsis,
    int runningTime,
    MovieRating rating,
    String posterUrls,
    LocalDate releaseDate,
    String distributorName,
    DirectorInfo director,
    List<ActorCastingInfo> actors,
    List<String> genres,
    List<String> screenTypes,
    int cumulativeBookings
) {

    public static MovieResponse from(Movie movie) {

        List<ActorCastingInfo> actors = movie.getMovieCasts().stream()
            .collect(Collectors.toMap(
                mc -> mc.getActor().getId(),
                mc -> new ActorCastingInfo(
                    mc.getActor().getId(),
                    mc.getActor().getName(),
                    mc.getActor().getPhotoUrl(),
                    mc.getRole(),
                    mc.getCastingType()
                ),
                (existing, replacement) -> existing
            ))
            .values()
            .stream()
            .collect(Collectors.toList());
            
        DirectorInfo director = new DirectorInfo(
            movie.getDirector().getId(),
            movie.getDirector().getName(),
            movie.getDirector().getPhotoUrl()
        );

        List<String> genres = movie.getGenres().stream()
            .map(Genre::getName)
            .collect(Collectors.toList());

        List<String> screenTypes = movie.getScreenTypes().stream()
            .map(ScreenType::getType)
            .collect(Collectors.toList());

        return new MovieResponse(
            movie.getId(),
            movie.getTitle(),
            movie.getSynopsis(),
            movie.getRunningTime(),
            movie.getRating(),
            movie.getPosterUrls().get(0),
            movie.getReleaseDate(),
            movie.getDistributor(),
            director,
            actors,
            genres,
            screenTypes,
            movie.getCumulativeBookings()
        );
    }
    
    public record DirectorInfo(
        Long id,
        String name,
        String photoUrl
    ) {}
    
    public record ActorCastingInfo(
        Long actorId,
        String actorName,
        String actorPhotoUrl,
        String role,
        CastingType castingType
    ) {}
} 
