package project.movie.review.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.common.web.response.ResponseDto;
import project.movie.review.dto.ReviewReqDto;
import project.movie.review.dto.ReviewRespDto;
import project.movie.review.service.ReviewService;

import java.util.List;

@RequestMapping("/api/review")
@RequiredArgsConstructor
@RestController
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    //클릭한 영화의 리뷰 조회
    @Operation(summary = "현재 영화의 리뷰 조회")
    @RequestMapping(value = "/lists/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "현재 영화의 리뷰 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))})

    })
    public ResponseEntity<?> getLists(@PathVariable Long id) {
        log.info("현재 영화의 리뷰 조회 실행");

        return new ResponseEntity<>(new ResponseDto<>(1, "현재 영화의 리뷰 조회 성공", reviewService.getList(id)), HttpStatus.OK);
    }

    //내가 작성한 리뷰 조회
    @Operation(summary = "본인이 작성한 리뷰 조회")
    @RequestMapping(value = "/lists/myList", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "나의 게시물 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> getMyList(@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails==null){
            throw new CustomApiException("로그인 후 확인하세요");
        }

        List<ReviewRespDto> reviewRespDtos = reviewService.getMyList(userDetails.getUsername());

        return new ResponseEntity<>(new ResponseDto<>(1, "나의 게시물 조회 성공",  reviewRespDtos), HttpStatus.OK);

    }

    // 게시물 작성
    @Operation(summary = "리뷰 작성")
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "리뷰 작성 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))})
    })
    public ResponseEntity<?> writeList(@RequestBody ReviewReqDto requestsDto,@AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails==null){
            throw new CustomApiException("로그인 후 작성하세요");
        }
//        Member member = memberService.getCurrentUserid();
//        requestsDto.setUserid(member);
        ReviewRespDto reviewRespDto = reviewService.writeList(requestsDto,userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 작성 성공", reviewRespDto), HttpStatus.OK);
    }

    //선택한 리뷰 삭제
    @Operation(summary = "선택한 리뷰 삭제 ")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "삭제 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 삭제하세요"),
    })
    public ResponseEntity<?> deleteList(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 삭제 성공", reviewService.deleteList(id,userDetails.getUsername())), HttpStatus.OK);
    }


    //내가 작성한 리뷰 수정
    @Operation(summary = "선택한 리뷰 수정")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시물 수정 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 수정하세요"),
    })
    public ResponseEntity<?> updateList(@PathVariable Long id, @RequestBody ReviewReqDto requestsDto , @AuthenticationPrincipal UserDetails userDetails)throws Exception{
        reviewService.updateList(id, requestsDto, userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 수정 성공", requestsDto), HttpStatus.OK);
    }

}
