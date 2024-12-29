package project.movie.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
    private int seq;
    private String memberId;
    private String title;
    private String content;
    private String theater;
    private int cate;
    private LocalDateTime reg_date;

    public static BoardDto toDto(BoardRespDto board) {
        return new BoardDto(
                board.getSeq(),
                board.getMemberId(),
                board.getTitle(),
                board.getContent(),
                board.getTheater(),
                board.getCate(),
                board.getReg_date());
    }


}
