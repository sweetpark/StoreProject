package project.movie.likes.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.likes.domain.Likes;

import java.time.LocalDateTime;
@Getter
@Setter
@ToString
public class LikesRespDto {

    private Long id;
    private LocalDateTime createDate;
    private String memberId;
    private Long movieId;

    public LikesRespDto(Likes likes) {
        this.id = likes.getId();
        this.createDate = likes.getCreateDate();
        this.memberId = likes.getMemberId().getMemberId();
        this.movieId = likes.getMovieId().getId();
    }
}
