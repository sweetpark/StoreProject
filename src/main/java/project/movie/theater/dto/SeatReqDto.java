package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@Builder
public class SeatReqDto {
    @Pattern(regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", message = "날짜는 YYYY-MM-DD 형식으로 입력해주세요")
    @Schema(description = "상영일자", required = true, example = "2023-07-15")
    private String scheduleDate;

    @Pattern(regexp = "^([01]\\d|2[0-3]):[0-5]\\d$", message = "시간은 HH:mm 형식으로 입력해주세요")
    @Schema(description = "상영시간", required = true, example = "14:30")
    private String startAt;

    @Schema(description = "상영관 코드", required = true, example = "1")
    private Long screenId;

    @Schema(description = "영화관 코드", required = true, example = "1")
    private Long theaterId;

    public LocalDate getScheduleDateAsLocalDate() {
        return LocalDate.parse(this.scheduleDate, DateTimeFormatter.ISO_DATE);
    }

    public LocalTime getStartAtAsLocalTime() {
        return LocalTime.parse(this.startAt, DateTimeFormatter.ISO_TIME);
    }
}
