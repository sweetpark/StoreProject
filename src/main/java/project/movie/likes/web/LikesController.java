package project.movie.likes.web;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.common.web.response.ResponseDto;
import project.movie.likes.dto.LikesReqDto;
import project.movie.likes.dto.LikesRespDto;
import project.movie.likes.service.LikesService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes")
public class LikesController {
    private final LikesService likesService;

    //좋아요 누르기
    @Operation(summary = "좋아요 누르기")
    @RequestMapping(value = "/doLike", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "좋아요 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> likeMovie(LikesReqDto likesReqDto, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new CustomApiException("로그인 후 이용해주세요.");
        }
        LikesRespDto likesRespDto = likesService.movieLike(likesReqDto, userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 성공",  likesRespDto), HttpStatus.OK);
    }

    //좋아요 취소
    @Operation(summary = "좋아요 취소")
    @RequestMapping(value = "/doUnLike/{movieId}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "좋아요 취소",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> unlikeMovie(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new CustomApiException("로그인 후 이용해주세요.");
        }
        LikesRespDto likesRespDto = likesService.unlikeMovie(userDetails.getUsername(), movieId);
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 취소 성공",  likesRespDto), HttpStatus.OK);
    }

    //영화별 좋아요 개수
    @Operation(summary = "선택한 영화 좋아요 개수")
    @RequestMapping(value = "/count/{movieId}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "선택한 영화 좋아요 개수 조회",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> countLikes(@PathVariable Long movieId) {
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 개수 조회 성공", likesService.countByMovieId(movieId)), HttpStatus.OK);
    }

    //유저가 이 영화 좋아요 눌렀는지 확인
    @Operation(summary = "이미 좋아요를 눌렀는지 확인")
    @RequestMapping(value = "/check/{movieId}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "좋아요 여부 조회",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> checkLikes(@PathVariable Long movieId, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new CustomApiException("로그인 후 이용해주세요.");
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "좋아요 여부 조회 성공", likesService.isLiked(userDetails.getUsername(), movieId)), HttpStatus.OK);
    }

    //내가 좋아요한 영화 목록
    @Operation(summary = "좋아요한 영화 목록")
    @RequestMapping(value = "/myLikes", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "내가 좋아요한 영화 목록 조회",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> myLikes(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            throw new CustomApiException("로그인 후 이용해주세요.");
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "내가 좋아요한 영화 목록 조회 성공", likesService.getMyLikedMovies(userDetails.getUsername())), HttpStatus.OK);
    }
}



