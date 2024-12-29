package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Screen;

@Getter
@Setter
@ToString
@Builder
public class ScreenResDto {
    @Schema(description = "상영관 고유 번호", required = true, example = "1")
    private Long id;

    @Schema(description = "상영관 번호", required = true, example = "1")
    private Integer screenNumber;

    @Schema(description = "영화관 정보", required = true, example = "1")
    private TheaterResDto theaterResDto;

    @Schema(description = "좌석 수", required = true, example = "138")
    private Integer seatCount; // 좌석 수

    public ScreenResDto(Long id, Integer screenNumber, TheaterResDto theaterResDto, Integer seatCount) {
        this.id = id;
        this.screenNumber = screenNumber;
        this.theaterResDto = theaterResDto;
        this.seatCount = seatCount;
    }

    public ScreenResDto(Screen screen) {
        this.id = screen.getId();
        this.screenNumber = screen.getScreenNumber();
        this.theaterResDto = TheaterResDto.from(screen.getTheater());
        this.seatCount = screen.getSeatCount();
    }

    public static ScreenResDto from (Screen screen) {
        return ScreenResDto.builder()
                .id(screen.getId())
                .screenNumber(screen.getScreenNumber())
                .theaterResDto(TheaterResDto.from(screen.getTheater()))
                .seatCount(screen.getSeatCount())
                .build();
    }
}
