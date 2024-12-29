package project.movie.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;
import project.movie.movie.repository.MovieRepository;
import project.movie.review.domain.Review;
import project.movie.review.dto.ReviewReqDto;
import project.movie.review.dto.ReviewRespDto;
import project.movie.review.repository.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final MovieRepository movieRepository;

    //클릭한 영화의 리뷰 조회
    @Transactional
    public List<ReviewRespDto> getList(Long id) {
        return reviewRepository.findByMovie_Id(id).stream()
                .map(ReviewRespDto::new)
                .collect(Collectors.toList());
    }

    //내가 작성한 리뷰 조회
    @Transactional
    public List<ReviewRespDto> getMyList(String userid) {
        return reviewRepository.findByMember_MemberId(userid).stream()
                .map(ReviewRespDto::new)
                .collect(Collectors.toList());
    }

    //리뷰 작성
    @Transactional
    public ReviewRespDto writeList(ReviewReqDto requestsDto,String userId) {
        requestsDto.setMember(userId); // Set the userId in the request DTO
        Review review = new Review(requestsDto, memberRepository, movieRepository);
        reviewRepository.save(review);
        return new ReviewRespDto(review);
    }

    //선택한 리뷰 삭제
    @Transactional
    public ReviewRespDto deleteList(Long id, String userId) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.");
        });

        if (!review.getMember().getMemberId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시물만 삭제할 수 있습니다.");
        }
        // 게시글이 있는 경우 삭제처리
        reviewRepository.deleteById(id);

        return new ReviewRespDto(review);
    }

    //내가 작성한 리뷰 수정
    @Transactional
    public ReviewRespDto updateList(Long id, ReviewReqDto requestsDto, String userId) throws Exception {
        Review review= reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
        );
        if (!review.getMember().getMemberId().equals(userId)) {
            throw new IllegalArgumentException("본인이 작성한 게시물만 수정할 수 있습니다.");
        }
        review.update(requestsDto);
        return new ReviewRespDto(review);
    }
}
