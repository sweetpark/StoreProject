package project.movie.theater.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import project.movie.common.domain.Base;
import project.movie.member.domain.Member;
import project.movie.movie.domain.PaymentMethod;

import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "reservations")
@Entity
@Getter
@ToString
public class Reservation extends Base {

    @Id
    @Column(name = "reservation_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name = "head_count")
    private Integer headCount; // 인원수

    private Long price;

    @Column(name = "payment_method", nullable = true)
    private PaymentMethod paymentMethod;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "is_payment_confirmed")
    private Boolean isPaymentConfirmed;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;


    @Builder
    public Reservation(String id, Member member, Schedule schedule, Seat seat, Integer headCount, Long price, PaymentMethod paymentMethod, String cardNumber, Boolean isPaymentConfirmed, LocalDateTime paidAt) {
        this.id = id;
        this.member = member;
        this.schedule = schedule;
        this.seat = seat;
        this.headCount = headCount;
        this.price = price;
        this.paymentMethod = paymentMethod;
        this.cardNumber = cardNumber;
        this.isPaymentConfirmed = isPaymentConfirmed;
        this.paidAt = paidAt;
    }
}
