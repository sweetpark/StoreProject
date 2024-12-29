package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import project.movie.member.service.MemberService;
import project.movie.movie.domain.PaymentMethod;
import project.movie.theater.domain.Reservation;
import project.movie.theater.service.ScheduleService;
import project.movie.theater.service.SeatService;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReservationSaveReqDto {
    @Schema(description = "예약 계정 아이디", required = true, example = "java.lang.Object")
    @NotNull
    private String memberId;

    @Schema(description = "영화 일정 아이디", required = true, example = "java.lang.Object")
    @NotNull
    private Long scheduleId;

    @Schema(description = "좌석 아이디", required = true, example = "java.lang.Object")
    @NotNull
    private Long seatId;

    @Schema(description = "인원수", required = true, example = "1")
    @NotNull
    private Integer headCount; // 인원수

    @Schema(description = "계산 금액", required = true, example = "70000")
    private Long price;

    @Schema(description = "결재 수단", required = false, example = "CREDIT_CARD | MOBILE")
    @Pattern(regexp = "CREDIT_CARD|MOBILE")
    private String paymentMethod;

    @Schema(description = "카드 번호", required = false, example = "1117281928329392")
    @Pattern(regexp = "^[0-9]{16}$", message = "카드 번호는 16자리 숫자로 입력해야 합니다.")
    private String cardNumber;

    @Schema(description = "결재유무체크", required = false, example = "TRUE | FALSE")
    private Boolean isPaymentConfirmed;

    @Schema(description = "결재일", required = false, example = "TRUE | FALSE")
    private LocalDateTime paidAt;

    @Builder
    public ReservationSaveReqDto(String memberId, Long scheduleId, Long seatId, Integer headCount, Long price, String paymentMethod, String cardNumber, Boolean isPaymentConfirmed, LocalDateTime paidAt) {
        this.memberId = memberId;
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.headCount = headCount;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.isPaymentConfirmed = isPaymentConfirmed;
        this.paidAt = paidAt;
    }

    public Reservation to(MemberService memberService, ScheduleService scheduleService, SeatService seatService) {
        return Reservation.builder()
                .id(UUID.randomUUID().toString())
                .member(memberService.getByMemberId(memberId))
                .schedule(scheduleService.get(scheduleId))
                .seat(seatService.get(seatId))
                .headCount(headCount)
                .price(price)
                .paymentMethod(PaymentMethod.valueOf(paymentMethod))
                .cardNumber(cardNumber)
                .isPaymentConfirmed(isPaymentConfirmed)
                .paidAt(LocalDateTime.now())
                .build();
    }

}
