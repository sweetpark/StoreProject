package project.movie.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.movie.board.domain.Board;
import project.movie.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class BoardRespDto {
    private int seq;
    private String title;
    private String content;
    private String theater;
    private int cate;
    private String memberId;
    private LocalDateTime reg_date;
    private String stored_filename; // 파일 이름
    private String original_filename; // 파일 이름
    private String filepath; // 파일 이름

    public BoardRespDto(Board board) {

        this.seq = board.getSeq();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.theater = board.getTheater();
        this.cate = board.getCate();
        this.memberId = board.getMember().getMemberId();
        this.reg_date = board.getReg_date();
        this.stored_filename = board.getStored_filename();
        this.original_filename = board.getOriginal_filename();
        this.filepath = board.getFilepath();
    }



}