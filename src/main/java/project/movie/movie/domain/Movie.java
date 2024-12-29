package project.movie.movie.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;
import project.movie.common.domain.Base;
import project.movie.movie.constants.MovieSyncConstants;
import project.movie.movie.dto.MovieDateGeneratorDTO;
import project.movie.movie.dto.MovieDetailSyncDTO;
import project.movie.movie.dto.MovieSyncDTO;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Entity
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "movies")
@Getter
@ToString
public class Movie extends Base {
    @Schema(description = "영화 고유 번호", required = true, example = "1")
    @Id
    @GeneratedValue
    @Column(name = "movie_id")
    private Long id;

    // 한글 제목
    @Schema(description = "영화제목", required = true, example = "헤리포터와 불의 잔")
    @NotBlank(message = "영화 제목은 공백 일 수 없습니다.")
    private String title;

    // 영어 제목
    @Schema(description = "영화제목(영문)", example = "Harry Potter and the Goblet of Fire")
    @Column(name = "title_en")
    private String titleEn;

    // 트레일러
    @Schema(description = "트레일러", example = "")
    private String trailer;

    // 줄거리
    @Schema(description = "줄거리", example = "해리 포터 일생일대 최대 난관! 요즘 들어 매일 꾸는 악몽 때문....")
    @Column(name = "plot", columnDefinition="BLOB")
    private String plot;

    // 포스터 이미지 경로
    @Schema(description = "포스터 이미지", example = "/images/poster/63xYQj1BwRFielxsBDXvHIJyXVm.jpg")
    @Column(name = "poster_image")
    private String posterImage;

    // 배너 이미지 경로
    @Schema(description = "배너 이미지", example = "/images/backdrop/18TSJF1WLA4CkymvVUcKDBwUJ9F.jpg")
    @Column(name = "backdrop_image")
    private String backdropImage;

    // 개봉년도
    @Schema(description = "개봉년도", example = "2024")
    @Column(name = "product_yaer")
    private Integer productYear;

    // 개봉일
    @Schema(description = "개봉일", example = "2024-10-09")
    @Column(name = "product_date")
    private String productDate;

    // 상영 시간
    @Schema(description = "상영시간 (분단위)", example = "107")
    @NotNull(message = "상영 시간은 Null 일 수 없습니다.")
    @Column(name = "show_time")
    private Integer showTime;

    // 상영일
    @Schema(description = "상영일", example = "2024-09-07")
    @NotNull(message = "상영일은 Null 일 수 없습니다.")
    @Column(name = "start_date")
    private LocalDate startDate;

    // 종영일
    @Schema(description = "종영일", example = "2024-12-07")
    @Column(name = "end_date")
    private LocalDate endDate;

    // 인기도
    @Schema(description = "인기도", example = "1435")
    @Column
    private Integer popularity;

    // 영화 코드
    @Schema(description = "영화코드", example = "1034541")
    @Column
    private Integer movieCd;

    @Builder
    public Movie(Long id, String title, String titleEn, String trailer, String plot, String posterImage, String backdropImage, Integer productYear, String productDate, Integer showTime, LocalDate startDate, LocalDate endDate, Integer popularity, Integer movieCd) {
        this.id = id;
        this.title = title;
        this.titleEn = titleEn;
        this.trailer = trailer;
        this.plot = plot;
        this.posterImage = posterImage;
        this.backdropImage = backdropImage;
        this.productYear = productYear;
        this.productDate = productDate;
        this.showTime = showTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.popularity = popularity;
        this.movieCd = movieCd;
    }

    /**
     * 영화 객체 생성
     *
     * @param movieSyncDTO 영화 기본 정보
     * @param movieDetailSyncDTO 영화 상세 정보
     * @param movieDateGeneratorDTO 영화 시작일, 종료일 객체
     *
     * @return Movie
     */
    public static Movie from(MovieSyncDTO movieSyncDTO, MovieDetailSyncDTO movieDetailSyncDTO, MovieDateGeneratorDTO movieDateGeneratorDTO) {
        return Movie.builder()
                .title(movieSyncDTO.getTitle())
                .titleEn(movieSyncDTO.getOriginalTitle())
                .plot(movieSyncDTO.getOverview())
                .posterImage(Paths.get(MovieSyncConstants.POSTER_IMG_FOLDER , movieSyncDTO.getPosterPath()).toString())
                .backdropImage(Paths.get(MovieSyncConstants.BACKDROP_IMG_FOLDER, movieSyncDTO.getBackdropPath()).toString())
                .showTime(movieDetailSyncDTO.getRuntime())
                .productYear(Integer.parseInt(StringUtils.substring(movieSyncDTO.getReleaseDate(), 0, 4)))
                .productDate(movieDetailSyncDTO.getReleaseDate())
                .popularity((int) movieDetailSyncDTO.getPopularity())
                .startDate(movieDateGeneratorDTO.getStartDate())
                .endDate(movieDateGeneratorDTO.getEndDate())
                .movieCd(movieSyncDTO.getId())
                .build();
    }

    @Transactional
    public Movie update(MovieSyncDTO movieSyncDTO, MovieDetailSyncDTO movieDetailSyncDTO, MovieDateGeneratorDTO movieDateGeneratorDTO) {
        this.title = movieSyncDTO.getTitle();
        this.titleEn = movieSyncDTO.getOriginalTitle();
        this.plot = movieSyncDTO.getOverview();
        this.posterImage = Paths.get(MovieSyncConstants.POSTER_IMG_FOLDER, movieSyncDTO.getPosterPath()).toString();
        this.backdropImage = Paths.get(MovieSyncConstants.BACKDROP_IMG_FOLDER, movieSyncDTO.getBackdropPath()).toString();
        this.showTime = movieDetailSyncDTO.getRuntime();
        this.productYear = Integer.parseInt(StringUtils.substring(movieSyncDTO.getReleaseDate(), 0, 4));
        this.productDate = movieDetailSyncDTO.getReleaseDate();
        this.popularity = (int) movieDetailSyncDTO.getPopularity();
        this.endDate = movieDateGeneratorDTO.getEndDate();

        return this;
    }


    /***
     사용 예시
     List<Movie> movies = movieRepository.findMoviesByStatus(MovieStatus.POPULAR.name());

     // 인기도로 오름차순 정렬
     List<Movie> popularityAscending = MovieSorter.sortMoviesBy(movies, Movie::getPopularity, true);

     // 제목으로 내림차순 정렬
     List<Movie> titleDescending = MovieSorter.sortMoviesBy(movies, Movie::getTitle, false);

     // 개봉일로 오름차순 정렬
     List<Movie> releaseDateAscending = MovieSorter.sortMoviesBy(movies, Movie::getReleaseDate, true);

     // 기존의 인기도 정렬 메서드도 그대로 사용 가능
     List<Movie> popularityDescending = MovieSorter.sortMoviesByPopularityDescending(movies);
     */
    public static <T extends Comparable<? super T>> List<Movie> sortMoviesBy(List<Movie> movies, Function<Movie, T> keyExtractor, boolean ascending) {
        Comparator<Movie> comparator = Comparator.comparing(keyExtractor);

        if (!ascending) {
            comparator = comparator.reversed();
        }

        return movies.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
