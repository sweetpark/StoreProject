package project.movie.movie.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.movie.movie.domain.Movie;
import project.movie.movie.dto.MovieAvailableReqDto;
import project.movie.movie.dto.MovieAvailableResDto;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findMoviesByTitleAndShowTimeAndProductYear(String title, Integer showTime, Integer ProductYear);
    Optional<List<Movie>> findMoviesByTitleOrShowTimeOrProductYear(String title, Integer showTime, Integer ProductYear);
    Optional<Movie> findByMovieCd(Integer movieCd);

    Page<Movie> findAll(Pageable pageable);

    // 인기 영화 목록 조회
    @Query("select m from Movie m where m.startDate <= current_date and m.endDate >= current_date order by m.popularity desc")
    List<Movie> findPopularMovies();

    // 최신 영화 목록 조회
    @Query("select m from Movie m where m.startDate <= current_date and m.endDate >= current_date order by m.productDate desc")
    List<Movie> findLatestMovies();

    // 예정 영화 목록 조회
    @Query("select m from Movie m where m.startDate > current_date")
    List<Movie> findUpcomingMovies();

    @Query("select m from Movie m where " +
            "(?1 = 'POPULAR' and m.startDate <= current_date and m.endDate >= current_date) or " +
            "(?1 = 'LATEST' and m.startDate <= current_date and m.endDate >= current_date) or " +
            "(?1 = 'UPCOMING' and m.startDate > current_date) " +
            "order by " +
            "case when ?1 = 'POPULAR' then m.popularity end desc, " +
            "case when ?1 = 'LATEST' then m.productDate end desc")
    List<Movie> findMoviesByStatus(String status);

    @Query("SELECT new project.movie.movie.dto.MovieAvailableResDto(m.id, m.title, m.titleEn,  " +
                "CASE WHEN EXISTS (" +
                "    SELECT 1 FROM Schedule s " +
                "    WHERE s.movie = m " +
                "    AND s.theater.id = :#{#paramMovie.theaterId} " +
                "    AND s.scheduleDate = :#{#paramMovie.getScheduleDateAsLocalDate()}" +
                ") THEN true ELSE false END as isWatchable) " +
                "FROM Movie m")
    List<MovieAvailableResDto> findAvailableMoviesByTheaterAndDate(@Param("paramMovie") MovieAvailableReqDto movieAvailableReqDto);
}
