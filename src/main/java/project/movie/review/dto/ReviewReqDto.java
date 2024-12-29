package project.movie.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.movie.member.domain.Member;
import project.movie.movie.domain.Movie;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReviewReqDto {

    @Schema(description = "게시물 번호", required = true, example = "1")
    private Long id;

    @Schema(description = "내용", required = true, example = "재미없어요...")
    private String content;

    @Schema(description = "별점", required = true, example = "1")
    private int star;

    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private String member;

    @Schema(description = "영화 아이디", required = true, example = "3")
    private long movie;
}
