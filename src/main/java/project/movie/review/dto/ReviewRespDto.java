package project.movie.review.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.review.domain.Review;

@Getter
@Setter
@ToString
public class ReviewRespDto {
    private Long id;
    private String content;
    private int star;
    private String member;
    private Long movie;

    public ReviewRespDto(Review review) {
        this.id = review.getId();
        this.content = review.getContent();
        this.star = review.getStar();
        this.member = review.getMember().getMemberId();
        this.movie = review.getMovie().getId();
    }
}
