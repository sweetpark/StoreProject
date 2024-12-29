package project.movie.likes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class LikesReqDto {
    @Schema(description = "좋아요 번호", required = true, example = "1")
    private Long id;

    @Schema(description = "좋아요 날짜", required = true, example = "2024-11-20")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime createDate;

    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private String memberId;

    @Schema(description = "영화 고유번호", required = true, example = "3")
    private Long movieId;
}
