package project.movie.theater.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.theater.dto.SeatAvailableResDto;
import project.movie.theater.dto.SeatReqDto;
import project.movie.theater.service.SeatService;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
@RequiredArgsConstructor
@Slf4j
public class SeatController {

    private final SeatService seatService;

    /**
     * 좌석 리스트 조회. 좌석 리스트에 각 좌석 예매 가능 여부 변수(isAvailable) 가 포함 되어 있음
     *
     * @param seatReqDto {
     *   scheduleDate: 영화 날짜
     *   bookingTime: 영화 시간
     *   screenId: 스크린 Id
     *   theaterId: 영화 Id
     * }
     * @return List
     */
    @Operation(summary = "좌석 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "좌석 리스트 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "액세스할 수 있는 권한이 없습니다."),
    })
    @GetMapping("/available")
    public ResponseEntity<ResponseDto<List<SeatAvailableResDto>>> listAvailableSeats(
        @Valid @Parameter(description = "좌석 리스트 요청 객체") SeatReqDto seatReqDto) {
        log.info("listAvailable 메서드 실행: {}", seatReqDto);

        List<SeatAvailableResDto> seatAvailableResDtos = seatService.findAvailableSeats(seatReqDto);

        return ResponseEntity.ok(new ResponseDto<>(1, "좌석 리스트 조회 성공", seatAvailableResDtos));
    }

}
