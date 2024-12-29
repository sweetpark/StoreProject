package project.movie.likes.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.movie.board.dto.BoardReqDto;
import project.movie.common.domain.Base;
import project.movie.likes.dto.LikesReqDto;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;
import project.movie.movie.domain.Movie;
import project.movie.movie.repository.MovieRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(
        name="likes",
        uniqueConstraints={
                @UniqueConstraint(
                        name = "likes_constraint",
                        columnNames={"movie_id", "member_id"}
                )
        }
)
public class Likes extends Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "좋아요 번호", required = true, example = "1")
    private Long id;

//    @Column(name="create_date")
//    @Schema(description = "좋아요 날짜", required = true, example = "2024-11-20")
//    private LocalDateTime createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private Member memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="movie_id")
    @Schema(description = "영화 고유번호", required = true, example = "3")
    private Movie movieId;

    public Likes(LikesReqDto requestsDto, MemberRepository memberRepository, MovieRepository movieRepository){
//        this.createDate= LocalDateTime.now();
        this.memberId = memberRepository.findByMemberId(requestsDto.getMemberId()).orElseThrow(
                () -> new IllegalArgumentException("해당 유저가 존재하지 않습니다.")
        );
        this.movieId = movieRepository.findById(requestsDto.getMovieId()).orElseThrow(
                () -> new IllegalArgumentException("해당 영화가 존재하지 않습니다.")
        );
    }
    // Getter
    public Movie getMovie() {
        return movieId;
    }
}
