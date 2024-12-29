package project.movie.board.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;
import project.movie.member.domain.Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardReqDto {
    //@Schema(description = "게시물 번호", required = true, example = "1")
    //private int seq;

    @Schema(description = "유저 아이디", required = true, example = "jungin2")
    private String member;

    @Schema(description = "제목", required = true, example = "문의드립니다")
    private String title;

    @Schema(description = "내용", required = true, example = "00개봉일 언제인가요")
    private String content;

    @Schema(description = "극장", required = true, example = "신도림")
    private String theater;

    @Schema(description = "문의유형", required = true, example = "1:문의|2:건의|3:칭찬|4:불만|5:기타")
    private int cate;

    @Schema(description = "작성일", required = true, example = "20210901")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime reg_date;

    @Schema(description = "저장된 파일이름", required = false, example = "fasdfasee.jpg")
    private String stored_filename; // 파일 이름

    @Schema(description = "파일 이름", required = false, example = "00.jpg")
    private String original_filename; // 파일 이름

    @Schema(description = "파일 경로", required = false, example = "파일경로")
    private String filepath; // 파일이 저장된 경로

}
