package project.movie.movie.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MovieAvailableResDto {
    @Schema(description = "영화 고유 번호", required = true, example = "1")
    private Long id;
    // 한글 제목
    @Schema(description = "영화제목", required = true, example = "헤리포터와 불의 잔")
    private String title;
    // 영어 제목
    @Schema(description = "영화제목(영문)", example = "Harry Potter and the Goblet of Fire")
    private String titleEn;
    @Schema(description = "영화 관람 가능 여부", example = "Harry Potter and the Goblet of Fire")
    private boolean isWatchable;

    public MovieAvailableResDto(Long id, String title, String titleEn, boolean isWatchable) {
        this.id = id;
        this.title = title;
        this.titleEn = titleEn;
        this.isWatchable = isWatchable;
    }
}