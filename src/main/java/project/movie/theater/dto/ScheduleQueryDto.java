package project.movie.theater.dto;

import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ScheduleQueryDto {
    private Long id;
    private String title;
    private String titleEn;
    private Integer showTime;
    private Integer screenNumber;
    private Integer seatCount;
    private String startAt;
    private Long totalSeats;
    private Long reservedSeats;
}
