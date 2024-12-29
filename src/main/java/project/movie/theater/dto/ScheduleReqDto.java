package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ScheduleReqDto {
    @Schema(description = "영화 예매 날짜", required = true, example = "2024-11-19")
    @NotNull(message = "영화 예매 날짜를 선택해주세요.")
    private String scheduleDate;

    @Schema(description = "영화관 고유 번호", required = true, example = "1")
    @NotNull(message = "영화관을 선택해주세요.")
    private String theaterId;

    @Schema(description = "영화 고유 번호", required = true, example = "1")
    @NotNull(message = "영화를 선택해주세요.")
    private String movieId;

    public LocalDate getScheduleDateAsLocalDate() {
        return LocalDate.parse(this.scheduleDate, DateTimeFormatter.ISO_DATE);
    }
}
