package project.movie.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.member.service.MemberService;
import project.movie.theater.domain.Reservation;
import project.movie.theater.dto.ReservationResDto;
import project.movie.theater.dto.ReservationSaveReqDto;
import project.movie.theater.repository.ReservationRepository;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final MemberService memberService;
    private final ScheduleService scheduleService;
    private final SeatService seatService;

    @Transactional
    public ReservationResDto create(ReservationSaveReqDto reservationSaveReqDto) {
        Reservation reservation = reservationRepository.save(reservationSaveReqDto.to(memberService, scheduleService, seatService));
        return ReservationResDto.from(reservation);
    }

    @Transactional
    public int update(Long id, boolean isPaymentConfirmed) {
        return reservationRepository.update(id, isPaymentConfirmed);
    }
}
