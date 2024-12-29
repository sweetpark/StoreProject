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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.theater.domain.Reservation;
import project.movie.theater.dto.ReservationResDto;
import project.movie.theater.dto.ReservationSaveReqDto;
import project.movie.theater.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "영화 예매")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "영화 예매 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
    })
    @PostMapping("/create")
    public ResponseEntity<?> create(
            @RequestBody @Valid @Parameter(description = "영화 예매 요청 객체") ReservationSaveReqDto reservationSaveReqDto) {
        log.info("ReservationController create 메서드 실행");
        ReservationResDto reservationResDto = reservationService.create(reservationSaveReqDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "영화 예매 성공", reservationResDto), HttpStatus.CREATED);
    }

    @Operation(summary = "예약 결재 여부 변경")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "예약 결재 여부 변경 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "400", description = "유효성 검사 실패"),
    })
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@Parameter(description = "영화 예약 고유 아이디") @PathVariable Long id, @Parameter(description = "영화 예약 결재 여부 변경 값") @RequestBody boolean isPaymentConfirmed) {
        log.info("ReservationController update 메서드 실행 아이디, 변경 값 :{}, {}", id, isPaymentConfirmed);
        int updateResNumber = reservationService.update(id, isPaymentConfirmed);
        return new ResponseEntity<>(new ResponseDto<>(1, "예약 결재 여부 변경 성공", updateResNumber), HttpStatus.OK);
    }
}