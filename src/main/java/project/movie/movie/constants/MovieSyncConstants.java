package project.movie.movie.constants;

public class MovieSyncConstants {
    public static final String POSTER_IMG_FOLDER = "/images/poster";
    public static final String BACKDROP_IMG_FOLDER = "/images/backdrop";
    public static final String JSON_BODY_FILED = "results"; // API 영화 바디 필드
    public static final String POSTER_IMG_FETCH_URL = "https://image.tmdb.org/t/p/w500/"; // 포스터 URL
    public static final String SHOWING_DATA_FETCH_URL = "https://api.themoviedb.org/3/movie/now_playing?language=ko&page=1&api_key=%s"; // 박스 오피스 영화 정보 FETCH URL
    public static final String UPCOMING_DATA_FETCH_URL = "https://api.themoviedb.org/3/movie/upcoming?language=ko&page=1&api_key=%s"; // 상영 예정 영화 정보 FETCH URL
    public static final String DETAIL_DATA_FETCH_URL = "https://api.themoviedb.org/3/movie/%d?language=ko&api_key=%s"; // 영화 상세 정보 FETCH URL
}
