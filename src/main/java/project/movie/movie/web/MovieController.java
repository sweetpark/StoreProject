package project.movie.movie.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.movie.domain.Movie;
import project.movie.movie.domain.MovieStatus;
import project.movie.movie.dto.MovieAvailableReqDto;
import project.movie.movie.dto.MovieResDto;
import project.movie.movie.dto.MovieAvailableResDto;
import project.movie.movie.service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
@Slf4j
public class MovieController {

    private final MovieService movieService;


    /**
     * 영화 상태에 따라 목록 조회 (인기작 | 최근 개봉작 | 상영 예정작)
     *
     * @param status [POPULAR | LATEST | UPCOMING]
     *
     * @return List
     */
    @Operation(summary = "영화 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화 목록 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "액세스할 수 있는 권한이 없습니다."),
    })
    @GetMapping("/status/{status}")
    public ResponseEntity<ResponseDto<List<MovieResDto>>> listByStatus(
            @PathVariable @Parameter(description = "영화 상태", schema = @Schema(allowableValues = {"POPULAR", "LATEST", "UPCOMING"})) String status) {
        log.info("영화 목록 조회 메서드 실행: {}", status);

//        try {
        MovieStatus movieStatus = MovieStatus.valueOf(status.toUpperCase());
        List<Movie> movies = movieService.getMoviesByStatus(movieStatus);

        List<MovieResDto> movieRespDtos = movies.stream().map(MovieResDto::from).toList();
        return ResponseEntity.ok(new ResponseDto<>(1, "영화 목록 조회 성공", movieRespDtos));
//        } catch (IllegalArgumentException e) {
//            log.warn("유효하지 않은 영화 상태: {}", status);
//            return ResponseEntity.badRequest().body(new ResponseDto<>(-1, "유효하지 않은 영화 상태입니다.", null));
//        }
    }

    /**
     * 영화 목록 정보 조회. 영화 목록에 각 영화 별 상영 가능 여부 변수(isWatchable) 가 포함 되어 있음
     *
     * @param movieAvailableReqDto {
     *   scheduleDate : 영화 예매 날짜
     *   theaterId : 영화관 고유 번호
     * }
     *
     * @return List
     */
    @Operation(summary = "예매 영화 목록 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화 목록 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "액세스할 수 있는 권한이 없습니다."),
    })
    @GetMapping("/available")
    public ResponseEntity<ResponseDto<List<MovieAvailableResDto>>> listAvailableMovies(
           @Valid @Parameter(description = "예매 영화 목록 요청 객체") MovieAvailableReqDto movieAvailableReqDto) {
        log.info("listAvailableMovies 메서드 실행: {}", movieAvailableReqDto);

        List<MovieAvailableResDto> movieRespDtos = movieService.findAvailableMovies(movieAvailableReqDto);

        return ResponseEntity.ok(new ResponseDto<>(1, "예매 영화 목록 조회 조회 성공", movieRespDtos));
    }

    // 인기 영화 목록 조회
    @Deprecated
    @GetMapping("/popular")
    public ResponseEntity<?> listPopularMovies() {
        List<Movie> popularMovies = movieService.findPopularMovies();
        List<MovieResDto> movieRespDtos = popularMovies.stream().map(MovieResDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "박스 오피스 영화 목록 조회 성공", movieRespDtos), HttpStatus.OK);
    }

    // 최신 영화 목록 조회
    @Deprecated
    @GetMapping("/latest")
    public ResponseEntity<?> listLatestMovies() {
        List<Movie> popularMovies = movieService.findLatestMovies();
        List<MovieResDto> movieRespDtos = popularMovies.stream().map(MovieResDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "박스 오피스 영화 목록 조회 성공", movieRespDtos), HttpStatus.OK);
    }

    // 예정 영화 목록 조회
    @Deprecated
    @GetMapping("/upcoming")
    public ResponseEntity<?> listUpcomingMovies() {
        List<Movie> popularMovies = movieService.findUpcomingMovies();
        List<MovieResDto> movieRespDtos = popularMovies.stream().map(MovieResDto::from).toList();
        return new ResponseEntity<>(new ResponseDto<>(1, "박스 오피스 영화 목록 조회 성공", movieRespDtos), HttpStatus.OK);
    }

    // 영화 상세 정보 조회
    @Operation(summary = "영화 상세 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화 상세 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "액세스할 수 있는 권한이 없습니다."),
    })

    @GetMapping("/{id}")
    public ResponseEntity<?> get(
            @PathVariable @Parameter(description = "영화 고유 번호", example = "1") Long id) {
        log.info("getByMovieId 메서드 실행: {}", id);
        Movie movie = movieService.getById(id);
        return new ResponseEntity<>(new ResponseDto<>(1, "영화 정보 조회 성공", MovieResDto.from(movie)), HttpStatus.OK);
    }
}
