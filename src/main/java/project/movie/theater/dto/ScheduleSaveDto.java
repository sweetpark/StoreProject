package project.movie.theater.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import project.movie.movie.domain.Movie;
import project.movie.theater.domain.Schedule;
import project.movie.theater.domain.Screen;
import project.movie.theater.domain.Theater;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleSaveDto {
    @NotNull
    private Movie movie;
    @NotNull
    private Theater theater;
    @NotNull
    private Screen screen;
    @NotNull
    private LocalDate scheduleDate;
    @NotNull
    private LocalTime startAt;
    @NotNull
    private LocalTime endAt;
    @NotNull
    private String code;

    @Builder
    public ScheduleSaveDto(Movie movie, Theater theater, Screen screen, LocalDate scheduleDate, LocalTime startAt, LocalTime endAt, String code) {
        this.movie = movie;
        this.theater = theater;
        this.screen = screen;
        this.scheduleDate = scheduleDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.code = code;
    }

    public Schedule to() {
        return Schedule.builder()
                .movie(movie)
                .theater(theater)
                .screen(screen)
                .scheduleDate(scheduleDate)
                .startAt(startAt)
                .endAt(endAt)
                .code(code)
                .build();
    }
}
