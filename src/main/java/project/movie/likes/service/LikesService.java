package project.movie.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.likes.domain.Likes;
import project.movie.likes.dto.LikesReqDto;
import project.movie.likes.dto.LikesRespDto;
import project.movie.likes.repository.LikesRepository;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;
import project.movie.movie.domain.Movie;
import project.movie.movie.dto.MovieResDto;
import project.movie.movie.repository.MovieRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LikesService {

    private final LikesRepository likesRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    //좋아요 누름
    @Transactional
    public LikesRespDto movieLike(LikesReqDto likesReqDto, String memberId) {
        // 이미 좋아요 누른 영화라면 예외 발생
        if (likesRepository.existsByMemberId_MemberIdAndMovieId_Id(memberId, likesReqDto.getMovieId())) {
            throw new IllegalArgumentException("이미 좋아요를 누른 영화입니다.");
        }

        // 좋아요 로직 추가
        Likes likes = new Likes(likesReqDto, memberRepository,movieRepository);
        likesRepository.save(likes);

        return new LikesRespDto(likes);
    }

    //좋아요 취소
    @Transactional
    public LikesRespDto unlikeMovie(String memberId, Long movieId) {
        Likes like = likesRepository.findByMemberId_MemberIdAndMovieId_Id(memberId, movieId)
                .orElseThrow(() -> new RuntimeException("좋아요 누른 적 없는 영화"));

        // 좋아요 삭제
        likesRepository.delete(like);
        return new LikesRespDto(like);
    }

    //영화별 좋아요 개수
    @Transactional
    public Long countByMovieId(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("영화 정보를 찾을 수 없습니다."));

        return likesRepository.countByMovieId_Id(movieId);

    }

    //유저가 이 영화 좋아요 했는지..
    @Transactional
    public boolean isLiked(String memberId, Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("영화 정보를 찾을 수 없습니다."));

        return likesRepository.existsByMemberId_MemberIdAndMovieId_Id(memberId, movieId);
    }

    //내가 좋아요한 영화들
    @Transactional(readOnly = true)
    public List<MovieResDto> getMyLikedMovies(String memberId) {

        return likesRepository.findByMemberId_MemberId(memberId).stream()
                .map(like -> new MovieResDto(like.getMovie()))
                .collect(Collectors.toList());
    }

}
