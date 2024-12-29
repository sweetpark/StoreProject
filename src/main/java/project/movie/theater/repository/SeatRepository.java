package project.movie.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.movie.theater.domain.Seat;
import project.movie.theater.dto.SeatAvailableResDto;
import project.movie.theater.dto.SeatReqDto;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT new project.movie.theater.dto.SeatAvailableResDto(se, " +
            "CASE WHEN EXISTS (" +
            "    SELECT 1 FROM Reservation r " +
            "    JOIN r.schedule sc " +
            "    WHERE sc.theater.id = :#{#paramSeat.theaterId} " +
            "    AND sc.screen.id = :#{#paramSeat.screenId} " +
            "    AND sc.scheduleDate = :#{#paramSeat.getScheduleDateAsLocalDate()} " +
            "    AND sc.startAt = :#{#paramSeat.getStartAtAsLocalTime()} " +
            "    AND r.seat.id = se.id " +
            ") THEN false ELSE true END as isAvailable) " +
            "FROM Seat se " +
            "WHERE se.theater.id = :#{#paramSeat.theaterId} " +
            "AND se.screen.id = :#{#paramSeat.screenId}")
    List<SeatAvailableResDto> findAvailableSeatsByTheaterAndScreenAndSchedule(@Param("paramSeat") SeatReqDto seatReqDto);
}
