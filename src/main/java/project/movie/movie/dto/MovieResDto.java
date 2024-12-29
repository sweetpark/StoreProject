package project.movie.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.movie.domain.Movie;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class MovieResDto {
    @Schema(description = "영화 고유 번호", required = true, example = "1")
    private Long id;
    // 한글 제목
    @Schema(description = "영화제목", required = true, example = "헤리포터와 불의 잔")
    private String title;
    // 영어 제목
    @Schema(description = "영화제목(영문)", example = "Harry Potter and the Goblet of Fire")
    private String titleEn;
    // 트레일러
    @Schema(description = "트레일러", example = "")
    private String trailer;
    // 줄거리
    @Schema(description = "줄거리", example = "해리 포터 일생일대 최대 난관! 요즘 들어 매일 꾸는 악몽 때문....")
    private String plot;
    // 포스터 이미지 경로
    @Schema(description = "포스터 이미지", example = "/images/poster/63xYQj1BwRFielxsBDXvHIJyXVm.jpg")
    private String posterImage;
    // 배너 이미지 경로
    @Schema(description = "배너 이미지", example = "/images/backdrop/18TSJF1WLA4CkymvVUcKDBwUJ9F.jpg")
    private String backdropImage;
    // 개봉일
    @Schema(description = "개봉년도", example = "2024")
    private Integer productYear;
    @Schema(description = "개봉일", example = "2024-10-09")
    private String productDate;
    // 상영 시간
    @Schema(description = "상영시간 (분단위)", example = "107")
    private Integer showTime;
    // 상영일
    @Schema(description = "상영일", example = "2024-09-07")
    private LocalDate startDate;
    // 종영일
    @Schema(description = "종영일", example = "2024-12-07")
    private LocalDate endDate;
    // 인기도
    @Schema(description = "인기도", example = "1435")
    private Integer popularity;
    // 영화 코드
    @Schema(description = "영화코드", example = "1034541")
    private Integer movieCd;

    public MovieResDto(Long id, String title, String titleEn, String trailer, String plot, String posterImage, String backdropImage, Integer productYear, String productDate, Integer showTime, LocalDate startDate, LocalDate endDate, Integer popularity, Integer movieCd) {
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

    public MovieResDto(Movie movie) {
        this.id = movie.getId();
        this.title = movie.getTitle();
        this.titleEn = movie.getTitleEn();
        this.trailer = movie.getTrailer();
        this.plot = movie.getPlot();
        this.posterImage = movie.getPosterImage();
        this.backdropImage = movie.getBackdropImage();
        this.productYear = movie.getProductYear();
        this.productDate = movie.getProductDate();
        this.showTime = movie.getShowTime();
        this.startDate = movie.getStartDate();
        this.endDate = movie.getEndDate();
        this.popularity = movie.getPopularity();
        this.movieCd = movie.getMovieCd();
    }

    public static MovieResDto from(Movie movie) {
        return MovieResDto.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .titleEn(movie.getTitleEn())
                .trailer(movie.getTrailer())
                .plot(movie.getPlot())
                .posterImage(movie.getPosterImage())
                .backdropImage(movie.getBackdropImage())
                .productYear(movie.getProductYear())
                .productDate(movie.getProductDate())
                .showTime(movie.getShowTime())
                .startDate(movie.getStartDate())
                .endDate(movie.getEndDate())
                .popularity(movie.getPopularity())
                .movieCd(movie.getMovieCd())
                .build();
    }
}
