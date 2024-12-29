package project.movie.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.theater.domain.Schedule;
import project.movie.theater.dto.ScheduleQueryDto;
import project.movie.theater.dto.ScheduleResDto;
import project.movie.theater.dto.ScheduleSaveDto;
import project.movie.theater.dto.ScheduleReqDto;
import project.movie.theater.repository.ScheduleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    @Transactional
    public void create(ScheduleSaveDto scheduleSaveDto) {
        // 1. 영화 시간표 저장
        scheduleRepository.save(scheduleSaveDto.to());
    }

    @Transactional(readOnly = true)
    public ScheduleResDto findShowTimes(ScheduleReqDto scheduleReqDto) {
        List<ScheduleQueryDto> schedules = scheduleRepository.findShowTimesByDateAndTheaterAndMovie(scheduleReqDto);
        return ScheduleResDto.transformSchedules(schedules);
    }

    @Transactional
    public Schedule get(Long scheduleId) {
        return scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new CustomApiException(scheduleId + "는 존재하지 않는 스케쥴 입니다"));
    }
}
