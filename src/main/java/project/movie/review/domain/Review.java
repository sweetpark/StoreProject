package project.movie.review.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import project.movie.board.dto.BoardReqDto;
import project.movie.common.domain.Base;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;
import project.movie.movie.domain.Movie;
import project.movie.movie.repository.MovieRepository;
import project.movie.review.dto.ReviewReqDto;
import project.movie.review.dto.ReviewRespDto;

@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter
@Slf4j
@Table(name = "reviews")
public class Review  extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "게시물 번호", required = true, example = "1")
    private Long id;

    @Column(name = "content")
    @Schema(description = "내용", required = true, example = "재미없어요...")
    private String content;

    @Column(name = "star")
    @Schema(description = "별점", required = true, example = "1")
    private int star;

    @ManyToOne
    @JoinColumn(name="member_id")
    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private Member member;

    @ManyToOne
    @JoinColumn(name="movie_id")
    @Schema(description = "영화 아이디", required = true, example = "3")
    private Movie movie;

    //업데이트
    public void update(ReviewReqDto requestsDto) {
        this.content = requestsDto.getContent();
        this.star = requestsDto.getStar();
    }

    public Review(ReviewReqDto requestsDto, MemberRepository memberRepository, MovieRepository movieRepository){
        this.id = requestsDto.getId();
        this.content = requestsDto.getContent();
        this.member = memberRepository.findByMemberId(requestsDto.getMember())
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        this.star = requestsDto.getStar();
        this.movie = movieRepository.findById(requestsDto.getMovie()).orElseThrow(() -> new RuntimeException("영화를 찾을 수 없습니다."));
    }
}
