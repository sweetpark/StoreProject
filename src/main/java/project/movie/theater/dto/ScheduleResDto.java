package project.movie.theater.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import project.movie.movie.dto.MovieResDto;
import project.movie.theater.domain.Schedule;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@Builder
public class ScheduleResDto {
    private Long id;
    private String title;
    private String titleEn;
    private int showTime;
    private List<ScreenScheduleDto> screenSchedules;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ScreenScheduleDto {
        private int screenNumber;
        private int totalSeats;
        private List<ScheduleDto> schedules;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class ScheduleDto {
        private String startTime;
        private int reservedSeats;
    }

    public static ScheduleResDto transformSchedules(List<ScheduleQueryDto> schedules) {
        if (schedules.isEmpty()) {
            return null;
        }

        // 영화 정보를 가져오기 위해 첫번째 row 데이터 접근
        ScheduleQueryDto firstSchedule = schedules.get(0);

        Map<Integer, List<ScheduleQueryDto>> groupedByScreen = schedules.stream()
                .collect(Collectors.groupingBy(ScheduleQueryDto::getScreenNumber));

        List<ScreenScheduleDto> screenSchedules = groupedByScreen.entrySet().stream()
                .map(entry -> createScreenScheduleDto(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return ScheduleResDto.builder()
                .id(firstSchedule.getId())
                .title(firstSchedule.getTitle())
                .titleEn(firstSchedule.getTitleEn())
                .showTime(firstSchedule.getShowTime())
                .screenSchedules(screenSchedules)
                .build();
    }

    private static ScreenScheduleDto createScreenScheduleDto(Integer screenNumber, List<ScheduleQueryDto> schedules) {
        return ScreenScheduleDto.builder()
                .screenNumber(screenNumber)
                .totalSeats(Math.toIntExact(schedules.get(0).getTotalSeats()))
                .schedules(createScheduleDtos(schedules))
                .build();
    }

    private static List<ScheduleDto> createScheduleDtos(List<ScheduleQueryDto> schedules) {
        return schedules.stream()
                .map(schedule -> ScheduleDto.builder()
                        .startTime(formatTime(schedule.getStartAt()))
                        .reservedSeats(Math.toIntExact(schedule.getReservedSeats()))
                        .build())
                .collect(Collectors.toList());
    }

    private static String formatTime(String time) {
        return LocalTime.parse(time).format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
