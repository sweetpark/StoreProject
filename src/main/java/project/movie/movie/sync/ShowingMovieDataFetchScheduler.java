package project.movie.movie.sync;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.common.util.file.upload.UploadFileUtils;
import project.movie.common.util.http.HttpConnector;
import project.movie.movie.domain.Movie;
import project.movie.movie.dto.MovieDateGeneratorDTO;
import project.movie.movie.dto.MovieDetailSyncDTO;
import project.movie.movie.dto.MovieSyncDTO;
import project.movie.movie.repository.MovieRepository;
import project.movie.movie.service.MovieService;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Optional;

import static project.movie.common.util.file.upload.UploadFileUtils.UPLOAD_PATH;
import static project.movie.common.util.file.upload.UploadFileUtils.uploadImage;
import static project.movie.movie.constants.MovieSyncConstants.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class ShowingMovieDataFetchScheduler {

    private final MovieService movieService;

    private final HttpConnector httpConnector;

    private final ObjectMapper objectMapper;

    private final MovieRepository movieRepository;

    @Value("${spring.tmdb.api-key}")
    private String apiKey;

    @Scheduled(cron = "0 0 6 * * ?")
    public void fetchMovieData() {
        log.info("ShowingMovieDataFetchScheduler fetchMovieData 스케쥴러 실행");
        try {
            // movieService.deleteAll(); // 영화 DB 데이터 삭제

            ResponseEntity<String> stringResponseEntity = httpConnector.sendHttpConnector(String.format(SHOWING_DATA_FETCH_URL, apiKey));

            // 통신 에러 시 스케쥴러 종료
            if (httpConnector.isError(stringResponseEntity.getStatusCode())) return;

            List<MovieSyncDTO> movieSyncDTOList = parseJsonDataAndReturn(stringResponseEntity.getBody());

            // 영화 정보 순회
            loopMovieSyncDTOList(movieSyncDTOList);
        } catch (Exception e) {
            log.error("[에러] ShowingMovieDataFetchScheduler fetchMovieData 에러 메시지: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<MovieSyncDTO> parseJsonDataAndReturn(String jsonData) throws JsonProcessingException {
        if (ObjectUtils.isEmpty(jsonData)) {
            log.error("jsonData : {}", jsonData);
            throw new CustomApiException("jsonData : 유효성 오류" + jsonData);
        }

        JsonNode movieJson = getMovieJsonNode(jsonData);
        return movieJson != null ? parseMovieJson(movieJson) : null;
    }

    public JsonNode getMovieJsonNode(String jsonData) {
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            return jsonNode.get(JSON_BODY_FILED);
        } catch (JsonProcessingException e) {
            // 예외 발생 시 로그를 남기고 null 반환
            log.error("getMovieJsonNode > Error parsing JSON data: {}", e.getMessage());
            return null;
        }
    }

    private List<MovieSyncDTO> parseMovieJson(JsonNode movieJson) {
        try {
            String movieJsonStr = movieJson.toString();
            return objectMapper.readValue(movieJsonStr, new TypeReference<List<MovieSyncDTO>>() {});
        } catch (JsonProcessingException e) {
            // 예외 발생 시 로그를 남기고 null 반환
            log.error("parseMovieJson > Error parsing JSON data: {}", e.getMessage());
            return null;
        }
    }

    public void loopMovieSyncDTOList(List<MovieSyncDTO> movieSyncDTOList) throws Exception {
        for (MovieSyncDTO movieSyncDTO : movieSyncDTOList) {
            uploadPosterImageUrl(movieSyncDTO); // 포스터 이미지 업로드 처리
            uploadBackdropImageUrl(movieSyncDTO); // 백드랍 이미지 업로드 처리
            saveMovieData(movieSyncDTO); // 영화 정보 저장
        }
    }

    public void uploadPosterImageUrl(MovieSyncDTO movieSyncDTO) throws MalformedURLException {
        String posterPath = movieSyncDTO.getPosterPath();
        String posterUrl = POSTER_IMG_FETCH_URL + posterPath;
        String posterUploadResponse = UploadFileUtils.uploadImage(UPLOAD_PATH, POSTER_IMG_FOLDER, posterUrl);
        log.info("posterUploadResponse: {}", posterUploadResponse);
    }

    public void uploadBackdropImageUrl(MovieSyncDTO movieSyncDTO) throws MalformedURLException {
        String backdropPath = movieSyncDTO.getBackdropPath();
        String backdropUrl = POSTER_IMG_FETCH_URL + backdropPath;
        String backdropUploadResponse = uploadImage(UPLOAD_PATH, BACKDROP_IMG_FOLDER, backdropUrl);
        log.info("backdropUploadResponse: {}", backdropUploadResponse);
    }

    public void saveMovieData(MovieSyncDTO movieSyncDTO) throws Exception {
        int movieCd = movieSyncDTO.getId();
        MovieDetailSyncDTO movieDetailSyncDTO = fetchMovieDetailData(movieCd); // 영화 상세 정보 불러오기
        MovieDateGeneratorDTO movieDateGeneratorDTO = new MovieDateGeneratorDTO();

        Optional<Movie> findMovieById = movieRepository.findByMovieCd(movieCd);

        if (findMovieById.isPresent()) {
            // Movie movie = findMovieById.get();
            // 기존 영화정보 존재하는 경우 정보만 변경
            // movie.update(movieSyncDTO, movieDetailSyncDTO, movieDateGeneratorDTO);
        } else {
            movieService.create(
                    Movie.from(
                            movieSyncDTO, // 영화 기본 정보
                            movieDetailSyncDTO, // 영화 상세 정보
                            movieDateGeneratorDTO.createShowingDate() // 상영 시작일, 상영 종료일 날짜 생성
                    )
            ); // 영화 정보 저장
        }
    }

    public MovieDetailSyncDTO fetchMovieDetailData(int movieId) throws Exception {
        ResponseEntity<String> stringResponseEntity = httpConnector.sendHttpConnector(buildMovieDetailFetchUrl(movieId));

        // 통신 에러 시 스케쥴러 종료
        if (httpConnector.isError(stringResponseEntity.getStatusCode())) return null;

        String jsonData = stringResponseEntity.getBody();

        return getAndParseMovieDetailJson(jsonData);
    }

    public MovieDetailSyncDTO getAndParseMovieDetailJson(String jsonData) throws Exception {
        try {
            JsonNode jsonNode = null;
            if (!ObjectUtils.isEmpty(jsonData)) {
                jsonNode = objectMapper.readTree(jsonData);
            }

            if (jsonNode == null) {
                return null;
            }

            return objectMapper.readValue(jsonNode.toString(), new TypeReference<MovieDetailSyncDTO>() {});
        } catch (JsonProcessingException e) {
            // 예외 발생 시 로그를 남기고 null 반환
            log.error("getAndParseMovieDetailJson > Error parsing JSON data: {}", e.getMessage());
            return null;
        }
    }

    public String buildMovieDetailFetchUrl(int movieId) {
        return String.format(
                DETAIL_DATA_FETCH_URL,
                movieId, apiKey
        );
    }
}
