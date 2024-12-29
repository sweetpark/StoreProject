package project.movie.movie.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@RequiredArgsConstructor
@ToString
public class MovieDetailSyncDTO {
    @JsonProperty("adult")
    private boolean adult;

    @JsonProperty("backdrop_path")
    private String backdropPath;

    @JsonProperty("belongs_to_collection")
    private BelongsToCollection belongsToCollection;

    @JsonProperty("budget")
    private Integer budget;

    @JsonProperty("genres")
    private List<Genre> genres;

    @JsonProperty("homepage")
    private String homepage;

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("imdb_id")
    private String imdbId;

    @JsonProperty("origin_country")
    private List<String> originCountry;

    @JsonProperty("original_language")
    private String originalLanguage;

    @JsonProperty("original_title")
    private String originalTitle;

    @JsonProperty("overview")
    private String overview;

    @JsonProperty("popularity")
    private double popularity;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("production_companies")
    private List<ProductionCompany> productionCompanies;

    @JsonProperty("production_countries")
    private List<ProductionCountry> productionCountries;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("revenue")
    private long revenue;

    @JsonProperty("runtime")
    private Integer runtime;

    @JsonProperty("spoken_languages")
    private List<SpokenLanguage> spokenLanguages;

    @JsonProperty("status")
    private String status;

    @JsonProperty("tagline")
    private String tagline;

    @JsonProperty("title")
    private String title;

    @JsonProperty("video")
    private boolean video;

    @JsonProperty("vote_average")
    private double voteAverage;

    @JsonProperty("vote_count")
    private Integer voteCount;

    // Nested classes with @JsonProperty annotations
    public static class BelongsToCollection {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("poster_path")
        private String posterPath;

        @JsonProperty("backdrop_path")
        private String backdropPath;

        // Getters and setters
    }

    public static class Genre {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("name")
        private String name;

        // Getters and setters
    }

    public static class ProductionCompany {
        @JsonProperty("id")
        private Integer id;

        @JsonProperty("logo_path")
        private String logoPath;

        @JsonProperty("name")
        private String name;

        @JsonProperty("origin_country")
        private String originCountry;

        // Getters and setters
    }

    public static class ProductionCountry {
        @JsonProperty("iso_3166_1")
        private String iso31661;

        @JsonProperty("name")
        private String name;

        // Getters and setters
    }

    public static class SpokenLanguage {
        @JsonProperty("english_name")
        private String englishName;

        @JsonProperty("iso_639_1")
        private String iso6391;

        @JsonProperty("name")
        private String name;

        // Getters and setters
    }

    // Main class getters and setters
}