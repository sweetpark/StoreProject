package project.movie.movie.dto;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Random;

@Getter
@ToString
public class MovieDateGeneratorDTO {

    private LocalDate startDate;
    private LocalDate endDate;

    // Showing movie 날짜 생성
    public MovieDateGeneratorDTO createShowingDate() {
        // 현재 날짜
        LocalDate today = LocalDate.now();

        // startDate는 오늘 날짜보다 이전이어야 하므로, 랜덤으로 1 ~ 30일 이전 날짜 생성
        Random random = new Random();
        long randomPastDays = random.nextInt(30) + 1;  // 1 ~ 30일 사이의 랜덤 숫자
        LocalDate startDate = today.minusDays(randomPastDays);

        // endDate는 오늘 이후여야 하므로, 랜덤으로 미래 날짜 생성
        long randomFutureDays = random.nextInt(15) + 15;  // 15 ~ 30일 사이의 랜덤 숫자
        LocalDate endDate = today.plusDays(randomFutureDays);

        // MovieDateGeneratorDTO 객체 생성 및 반환
        MovieDateGeneratorDTO movieDateGeneratorDTO = new MovieDateGeneratorDTO();
        movieDateGeneratorDTO.startDate = startDate;
        movieDateGeneratorDTO.endDate = endDate;

        return movieDateGeneratorDTO;
    }

    // Upcoming movie 날짜 생성
    public MovieDateGeneratorDTO createUpcomingDate() {
        // 현재 날짜
        LocalDate today = LocalDate.now();

        // startDate는 미래의 날짜여야 하므로, 랜덤으로 미래 날짜 생성
        Random random = new Random();
        long randomFutureStartDays = random.nextInt(15) + 10;  // 1 ~ 30일 사이의 랜덤 숫자
        LocalDate startDate = today.plusDays(randomFutureStartDays);

        // endDate는 startDate 이후로 랜덤한 기간을 두고 생성
        long randomDurationDays = randomFutureStartDays + 30;  // 15 ~ 30일 사이의 랜덤 숫자
        LocalDate endDate = today.plusDays(randomDurationDays);

        // MovieDateGeneratorDTO 객체 생성 및 반환
        MovieDateGeneratorDTO movieDateGeneratorDTO = new MovieDateGeneratorDTO();
        movieDateGeneratorDTO.startDate = startDate;
        movieDateGeneratorDTO.endDate = endDate;

        return movieDateGeneratorDTO;
    }

}
