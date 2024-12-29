package project.movie.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class MovieSyncDTO {
    private boolean adult; // 성인 여부
    @JsonProperty("backdrop_path")
    private String backdropPath; //
    @JsonProperty("genre_ids")
    private List<Integer> genreIds; // 장르
    private int id; // 영화 코드
    @JsonProperty("original_language")
    private String originalLanguage; // 오리지널 영화 코드
    @JsonProperty("original_title")
    private String originalTitle; // 오리지널 영화 제목
    private String overview; // 줄거리
    private double popularity; // 인기
    @JsonProperty("poster_path")
    private String posterPath; // 포스터 이미지 경로
    @JsonProperty("release_date")
    private String releaseDate; // 개봉일
    private String title; // 영화 제목
    private boolean video; //
    @JsonProperty("vote_average")
    private double voteAverage; // 투표 평균
    @JsonProperty("vote_count")
    private int voteCount; // 투표 수
}
