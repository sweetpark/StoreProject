package project.movie.movie.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.movie.domain.Movie;
import project.movie.movie.domain.MovieStatus;
import project.movie.movie.dto.MovieAvailableReqDto;
import project.movie.movie.dto.MovieResDto;
import project.movie.movie.dto.MovieAvailableResDto;
import project.movie.movie.repository.MovieRepository;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository movieRepository;
    @Transactional
    public MovieResDto create(Movie movie) {
        // 1. 영화 엔티티 저장
        Movie movieResponse = movieRepository.save(movie);

        // 2. dto 응답
        return new MovieResDto(movieResponse);
    }

    @Transactional
    public void deleteAll() {
        movieRepository.deleteAll();
    }

    // 영화 목록 조회
    public List<Movie> list() {
        return movieRepository.findAll();
    }

    public Movie getById(Long movieId) {
        return movieRepository.findById(movieId)
                .orElseThrow(() -> new CustomApiException(movieId + " 는 존재하지 않는 영화 ID 입니다"));
    }

    public List<Movie> findPopularMovies() {
        return movieRepository.findPopularMovies();
    }

    public List<Movie> findLatestMovies() {
        return movieRepository.findLatestMovies();
    }

    public List<Movie> findUpcomingMovies() {
        return movieRepository.findUpcomingMovies();
    }

    public List<Movie> getMoviesByStatus(MovieStatus status) {
        return movieRepository.findMoviesByStatus(status.name());
    }

    public List<MovieAvailableResDto> findAvailableMovies(MovieAvailableReqDto movieAvailableReqDto) {
        List<MovieAvailableResDto> availableMoviesByTheaterAndDate = movieRepository.findAvailableMoviesByTheaterAndDate(movieAvailableReqDto);
        sortMethod(availableMoviesByTheaterAndDate);

        return availableMoviesByTheaterAndDate;
    }

    public void sortMethod(List<MovieAvailableResDto> list) {
        Collections.sort(list, new Comparator<MovieAvailableResDto>() {
            @Override
            public int compare(MovieAvailableResDto o1, MovieAvailableResDto o2) {
                if (o1.isWatchable() != o2.isWatchable()) {
                    return o2.isWatchable() ? 1 : -1;
                }

                return o1.getTitle().compareTo(o2.getTitle());
            }
        });
    }
}
