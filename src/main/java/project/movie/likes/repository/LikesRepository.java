package project.movie.likes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import project.movie.likes.domain.Likes;
import project.movie.member.domain.Member;
import project.movie.movie.domain.Movie;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {

    boolean existsByMemberId_MemberIdAndMovieId_Id(String memberId, Long movieId); // 좋아요 여부 확인
    Optional<Likes> findByMemberId_MemberIdAndMovieId_Id(String memberId, Long id); // 취소를 위해 추가
    Long countByMovieId_Id(@Param("movieId") Long movieId); // 영화별 좋아요 개수

    List<Likes> findByMemberId_MemberId(String memberId);
}
