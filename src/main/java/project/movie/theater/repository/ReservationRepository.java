package project.movie.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.movie.theater.domain.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Modifying
    @Query("UPDATE Reservation r set r.isPaymentConfirmed = :isPaymentConfirmed where r.id = :id")
    int update(@Param("id") Long id, @Param("isPaymentConfirmed") boolean isPaymentConfirmed);

//    @Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId AND s.id NOT IN " +
//            "(SELECT r.seat.id FROM Reservation r WHERE r.schedule.id = :scheduleId)")
//    List<Seat> listAvailableSeats(@Param("screenId") Long screenId, @Param("scheduleId") Long scheduleId);

//    @Query("SELECT s FROM Seat s WHERE s.screen.id = :screenId AND s.id NOT IN " +
//            "(SELECT r.seat.id FROM Reservation r WHERE r.schedule.id = :scheduleId)")
//    @Query("SELECT new project.movie.movie.dto.MovieWithWatchAbilityResDto(s, " +
//            "CASE WHEN EXISTS (" +
//            "    SELECT 1 FROM Reservation r " +
//            "    WHERE r.movie = m " +
//            "    AND r.schedule.scheduleDate = :scheduleDate" +
//            "    AND r.schedule.theater.id = :theaterId " +
//            "    AND r.schedule.screen = :screenId" +
//            "    AND r.schedule.movie.id = :movieId" +
//            "    AND r.schedule.scheduleDate = :scheduleDate" +
//            "    AND r.schedule.startAt = :startAt" +
//            ") THEN false ELSE true END as isNotReserable) " +
//            "FROM Seat s")
//    List<Seat> listAvailable(@Param("scheduleDate") String scheduleDate, @Param("theaterId") String theaterId, @Param("screenId") Long screenId, @Param("movieId") String movieId, @Param("startAt") String startAt);

//    @Query("SELECT new project.movie.movie.dto.MovieWithWatchAbilityResDto(m, " +
//            "CASE WHEN EXISTS (" +
//            "    SELECT 1 FROM Schedule s " +
//            "    WHERE s.movie = m " +
//            "    AND s.theater.id = :#{#paramMovie.theaterId} " +
//            "    AND s.scheduleDate = :#{#paramMovie.scheduleDate}" +
//            ") THEN false ELSE true END as isAvailable) " +
//            "FROM Movie m")
//    List<MovieWithWatchAbilityResDto> listWithWatchAbilityDtoByDateAndTheater(@Param("paramMovie") MovieWithWatchAbilityReqDto movieWithWatchAbilityReqDto);
}