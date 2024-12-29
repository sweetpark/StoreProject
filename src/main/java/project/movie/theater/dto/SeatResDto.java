package project.movie.theater.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Seat;

@Getter
@Setter
@Builder
@ToString
public class SeatResDto {
    @Schema(description = "좌석코드", required = true, example = "1")
    private Long id;

    @JsonIgnore
    @Schema(description = "상영관 코드", required = true, example = "1")
    private ScreenResDto screenResDto;

    @JsonIgnore
    @Schema(description = "영화관 코드", required = true, example = "1")
    private TheaterResDto theaterResDto;

    @Schema(description = "좌석 그룹", required = true, example = "A")
    private String seatGroup;

    @Schema(description = "좌석 번호", required = true, example = "1")
    private Integer seatNo;

    @Schema(description = "좌석 줄번호", required = true, example = "1")
    private Integer seatLineNo;

    @JsonIgnore
    @Schema(description = "예매 가능 여부", required = true, example = "true | false")
    private Boolean isBookable; // 예매 가능 여부

    public SeatResDto(Long id, ScreenResDto screenResDto, TheaterResDto theaterResDto, String seatGroup, Integer seatNo, Integer seatLineNo, Boolean isBookable) {
        this.id = id;
        this.screenResDto = screenResDto;
        this.theaterResDto = theaterResDto;
        this.seatGroup = seatGroup;
        this.seatNo = seatNo;
        this.seatLineNo = seatLineNo;
        this.isBookable = isBookable;
    }

    @Builder
    public SeatResDto(Seat seat) {
        this.id = seat.getId();
        this.screenResDto = ScreenResDto.from(seat.getScreen());
        this.theaterResDto = TheaterResDto.from(seat.getTheater());
        this.seatGroup = seat.getSeatGroup();
        this.seatNo = seat.getSeatNo();
        this.seatLineNo = seat.getSeatLineNo();
        this.isBookable = seat.getIsBookable();
    }

    public static SeatResDto from(Seat seat) {
        return new SeatResDto(seat);
    }
}
