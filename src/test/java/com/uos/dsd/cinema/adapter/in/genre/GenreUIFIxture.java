package com.uos.dsd.cinema.adapter.in.genre;

import com.uos.dsd.cinema.adaptor.in.web.genre.request.CreateGenreRequest;
import com.uos.dsd.cinema.adaptor.in.web.genre.request.UpdateGenreRequest;

public class GenreUIFIxture {

    public static final String GENRE_NAME = "test genre";
    public static final String GENRE_DESCRIPTION = "test description";
    public static final String GENRE_IMAGE_URL = "test image url";
    
    public static CreateGenreRequest createGenreRequest = new CreateGenreRequest(GENRE_NAME, GENRE_DESCRIPTION, GENRE_IMAGE_URL);

    public static UpdateGenreRequest updateGenreRequest = new UpdateGenreRequest(GENRE_DESCRIPTION, GENRE_IMAGE_URL);
}
