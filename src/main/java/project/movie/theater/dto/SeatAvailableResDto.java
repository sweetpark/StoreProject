package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.theater.domain.Seat;

@Getter
@Setter
@ToString
public class SeatAvailableResDto {
    @Schema(description = "좌석 정보", required = true)
    private SeatResDto seatResDto;
    @Schema(description = "예매 불가능 여부", required = true, example = "true | false")
    private boolean isAvailable;

    @Builder
    public SeatAvailableResDto(Seat seat, boolean isAvailable) {
        this.seatResDto = SeatResDto.from(seat);
        this.isAvailable = isAvailable;
    }

    public static SeatAvailableResDto from(Seat seat) {
        return SeatAvailableResDto.builder()
                .seat(seat)
                .isAvailable(!seat.getIsBookable())
                .build();
    }
}
