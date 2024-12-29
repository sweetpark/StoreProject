package project.movie.theater.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.movie.common.web.response.ResponseDto;
import project.movie.theater.dto.TheaterResDto;
import project.movie.theater.service.TheaterService;

import java.util.List;

@RestController
@RequestMapping("/api/theaters")
@RequiredArgsConstructor
@Slf4j
public class TheaterController {
    private final TheaterService theaterService;

    @Operation(summary = "영화관 목록 정보")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "영화관 목록 정보 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "액세스할 수 있는 권한이 없습니다."),
    })
    @GetMapping
    public ResponseEntity<ResponseDto<List<TheaterResDto>>> list() {
        log.info("TheaterController > list START");
        List<TheaterResDto> list = theaterService.findAll();
        log.info("TheaterController > list END");
        return ResponseEntity.ok(new ResponseDto<>(1, "영화관 목록 정보 조회 성공", list));
    }
}