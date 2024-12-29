package project.movie.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.movie.theater.domain.Schedule;
import project.movie.theater.dto.ScheduleQueryDto;
import project.movie.theater.dto.ScheduleReqDto;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("SELECT DISTINCT new project.movie.theater.dto.ScheduleQueryDto(" +
                "s.movie.id," +
                "s.movie.title," +
                "s.movie.titleEn," +
                "s.movie.showTime," +
                "s.screen.screenNumber," +
                "s.screen.seatCount," +
                "CASE " +
                "  WHEN s.startAt IS NOT NULL THEN CAST(s.startAt AS string)" +
                "  ELSE ''" +
                "END, " +
                "(SELECT COUNT(seat) FROM Seat seat WHERE seat.screen = s.screen) as totalSeats, " +
                "(SELECT COUNT(r) FROM Reservation r WHERE r.schedule.id = s.id) as reservedSeats" +
            ") " +
            "FROM Schedule s " +
            "WHERE s.theater.id = :#{#paramScheduler.theaterId} AND s.movie.id = :#{#paramScheduler.movieId} AND s.scheduleDate = :#{#paramScheduler.getScheduleDateAsLocalDate()} ")
            // "ORDER BY s.screen.screenNumber, s.startAt")
    List<ScheduleQueryDto> findShowTimesByDateAndTheaterAndMovie(@Param("paramScheduler") ScheduleReqDto scheduleReqDto);
}
