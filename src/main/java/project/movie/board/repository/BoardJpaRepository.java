package project.movie.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardRespDto;

import java.util.List;

public interface BoardJpaRepository extends JpaRepository<Board, Integer> {
    List<Board> findByMember_MemberId(String memberId);

}
