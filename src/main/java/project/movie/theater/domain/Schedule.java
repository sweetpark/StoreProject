package project.movie.theater.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.common.domain.Base;
import project.movie.movie.domain.Movie;

import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "schedules")
@Entity
@Getter
@Slf4j
@ToString
public class Schedule extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theater_id")
    private Theater theater;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "screen_id")
    private Screen screen;

    @Column(name = "schedule_date")
    private LocalDate scheduleDate;

    @Column(name = "start_at")
    private LocalTime startAt;

    @Column(name = "end_at")
    private LocalTime endAt;

    private String code;

    @Builder
    public Schedule(Long id, Movie movie, Theater theater, Screen screen, LocalDate scheduleDate, LocalTime startAt, LocalTime endAt, String code) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.screen = screen;
        this.scheduleDate = scheduleDate;
        this.startAt = startAt;
        this.endAt = endAt;
        this.code = code;
    }
}
