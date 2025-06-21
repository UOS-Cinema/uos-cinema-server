package com.uos.dsd.cinema.domain.movie;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"movieId", "actorId"})
public class MovieCastId implements Serializable {

    private Long movieId;

    private Long actorId;
} 
