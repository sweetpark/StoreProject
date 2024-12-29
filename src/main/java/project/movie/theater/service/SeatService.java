package project.movie.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.theater.domain.Seat;
import project.movie.theater.dto.SeatAvailableResDto;
import project.movie.theater.dto.SeatReqDto;
import project.movie.theater.repository.SeatRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    @Transactional(readOnly = true)
    public List<SeatAvailableResDto> findAvailableSeats(SeatReqDto seatReqDto) {
        return seatRepository.findAvailableSeatsByTheaterAndScreenAndSchedule(seatReqDto);
    }

    @Transactional
    public Seat get(Long seatId) {
        return seatRepository.findById(seatId)
                .orElseThrow(() -> new CustomApiException(seatId + " 는 존재하지 않는 좌석 입니다"));
    }
}
