package project.movie.theater.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.member.domain.Member;
import project.movie.member.dto.MemberRespDto;
import project.movie.movie.domain.PaymentMethod;
import project.movie.theater.domain.Reservation;
import project.movie.theater.domain.Schedule;
import project.movie.theater.domain.Seat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
public class ReservationResDto {
    private MemberRespDto memberRespDto;

    @JsonIgnore
    private ScheduleResDto scheduleResDto;

    private SeatResDto seatResDto;

    private Integer headCount; // 인원수

    private Long price;

    private PaymentMethod paymentMethod;

    private String cardNumber;

    private Boolean isPaymentConfirmed;

    private LocalDateTime paidAt;

    public static ReservationResDto from(Reservation reservation) {
        return ReservationResDto.builder()
                .memberRespDto(MemberRespDto.from(reservation.getMember()))
                // .scheduleResDto(ScheduleResDto.from(reservation.getSchedule()))
                .seatResDto(SeatResDto.from(reservation.getSeat()))
                .headCount(reservation.getHeadCount())
                .price(reservation.getPrice())
                .paymentMethod(reservation.getPaymentMethod())
                .cardNumber(reservation.getCardNumber())
                .isPaymentConfirmed(reservation.getIsPaymentConfirmed())
                .paidAt(reservation.getPaidAt())
                .build();
    }
}
