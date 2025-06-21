package com.uos.dsd.cinema.domain.screening;

import com.uos.dsd.cinema.common.exception.http.BadRequestException;
import com.uos.dsd.cinema.common.exception.http.ForbiddenException;
import com.uos.dsd.cinema.common.model.Base;
import com.uos.dsd.cinema.domain.movie.Movie;
import com.uos.dsd.cinema.domain.screening.constraint.ScreeningConstraint;
import com.uos.dsd.cinema.domain.screening.exception.ScreeningExceptionCode;
import com.uos.dsd.cinema.domain.screening.utils.RunningTimeUtil;
import com.uos.dsd.cinema.domain.theater.Theater;
import com.uos.dsd.cinema.domain.screen_type.ScreenType;

import lombok.Getter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import jakarta.persistence.PrePersist;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "screenings")
@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true, of = "id")
@NoArgsConstructor
public class Screening extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_id", nullable = false)
    private Long movieId;

    @Column(name = "theater_id", nullable = false)
    private Long theaterId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "screen_type", referencedColumnName = "type")
    private ScreenType screenType;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private int duration;

    @Transient
    private LocalDateTime endTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id", insertable = false, updatable = false)
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theater_id", insertable = false, updatable = false)
    private Theater theater;

    public Screening(
            Movie movie,
            Long theaterId, 
            String screenType, 
            LocalDateTime startTime,
            List<Screening> existingScreenings) {

        if (movie == null) {
            throw new IllegalArgumentException("Movie cannot be null");
        }
        if (movie.getDeletedAt() != null) {
            throw new ForbiddenException(ScreeningExceptionCode.DELETED_MOVIE);
        }
        if (theaterId == null) {
            throw new IllegalArgumentException("Theater ID cannot be null");
        }
        validateScreenType(movie, screenType);
        validateStartTime(startTime, existingScreenings);

        this.movie = movie;
        this.movieId = movie.getId();
        this.theaterId = theaterId;
        this.screenType = ScreenType.reference(screenType);
        this.startTime = startTime;
        this.duration = calculateDuration(movie.getRunningTime());
        if (ScreeningConstraint.isInvalidDuration(this.duration)) {
            throw new BadRequestException(ScreeningExceptionCode.INVALID_DURATION);
        }
        setEndTime();
    }

    @PrePersist
    private void setEndTime() {

        this.endTime = this.startTime.plusMinutes(this.duration);
    }

    public void updateStartTime(LocalDateTime startTime,
            List<Screening> existingScreenings) {

        validateStartTime(startTime, existingScreenings);
        this.startTime = startTime;
        setEndTime();
    }

    private void validateStartTime(LocalDateTime startTime, 
            List<Screening> existingScreenings) {

        if (startTime.isBefore(LocalDateTime.now())) {
            throw new BadRequestException(ScreeningExceptionCode.IN_PAST_START_TIME);
        }

        if (existingScreenings.isEmpty()) {
            return;
        }
        for (Screening existingScreening : existingScreenings) {
            if (startTime.isBefore(existingScreening.getEndTime())
                    && this.endTime.isAfter(existingScreening.getStartTime())) {
                throw new BadRequestException(ScreeningExceptionCode.OVERLAPPING_START_TIME);
            }
        }
    }
    
    private void validateScreenType(Movie movie, String screenType) {

        if (screenType == null) {
            throw new IllegalArgumentException("Screen type cannot be null");
        }
        if (!movie.getScreenTypes().contains((ScreenType) ScreenType.reference(screenType))) {
            throw new BadRequestException(
                    ScreeningExceptionCode.SCREEN_TYPE_NOT_SUPPORTED_MOVIE);
        }
    }

    private int calculateDuration(int movieRunningTime) {

        return RunningTimeUtil.calculateRunningTime(movieRunningTime);
    }
}
