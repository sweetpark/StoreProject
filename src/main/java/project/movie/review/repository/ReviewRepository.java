package project.movie.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.board.domain.Board;
import project.movie.review.domain.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {

    List<Review> findByMovie_Id(Long movieId);

    List<Review> findByMember_MemberId(String memberId);

}
