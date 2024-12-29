package project.movie.board.web;

import org.springframework.core.io.Resource;
import org.springframework.core.io.InputStreamResource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.movie.auth.jwt.dto.CustomUserDetails;
import project.movie.auth.jwt.service.CustomUserDetailsService;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardDto;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.board.service.BoardService;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.member.domain.Member;
import project.movie.member.dto.MemberRespDto;
import project.movie.member.service.MemberService;
import project.movie.common.web.response.ResponseDto;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/board")
@RequiredArgsConstructor
@RestController
@Slf4j
public class BoardController {
    private final MemberService memberService;
    private final BoardService boardService;


    // 전체 게시물 조회
    @Operation(summary = "전체 게시물 조회")
    @RequestMapping(value = "/lists", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "전체 게시물 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> getLists() {
        log.info("전체 게시물 가져오기 실행");

        return new ResponseEntity<>(new ResponseDto<>(1, "전체 게시물 조회 성공", boardService.getLists()), HttpStatus.OK);
    }

    //선택한 게시물 조회
    @Operation(summary = "선택한 게시물 조회")
    @RequestMapping(value = "/lists/{id}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "선택 게시물 조회 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 확인하세요"),
    })
    public ResponseEntity<?> getList(@PathVariable int id) {

        return new ResponseEntity<>(new ResponseDto<>(1, "선택한 게시물 조회 성공",  boardService.getList(id)), HttpStatus.OK);
    }

    //본인이 작성한 게시물 조회
    @Operation(summary = "본인이 작성한 게시물 조회")
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

        List<BoardRespDto> boardRespDtoList = boardService.getMyList(userDetails.getUsername());

        return new ResponseEntity<>(new ResponseDto<>(1, "나의 게시물 조회 성공",  boardRespDtoList), HttpStatus.OK);

    }

    // 게시물 작성
    @Operation(summary = "게시물 작성")
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 작성하세요"),
    })
    public ResponseEntity<?> writeList(@RequestBody BoardReqDto requestsDto, @AuthenticationPrincipal UserDetails userDetails) {
        if(userDetails==null){
            throw new CustomApiException("로그인 후 작성하세요");
        }
//        Member member = memberService.getCurrentUserid();
//        requestsDto.setUserid(member);
        BoardRespDto boardRespDto = boardService.writeList(requestsDto,userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 작성 성공", boardRespDto), HttpStatus.OK);

    }

    @Operation(summary = "게시물 작성 첨부파일 포함")
    @RequestMapping(value = "/writeFile", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "게시물 작성 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 작성하세요"),
    })
    public ResponseEntity<?> writeListFile(BoardReqDto requestsDto, @AuthenticationPrincipal UserDetails userDetails ,@RequestParam("file")MultipartFile file) throws IOException {
        if(userDetails==null){
            throw new CustomApiException("로그인 후 작성하세요");
        }

        BoardRespDto boardRespDto = boardService.writeListFile(requestsDto,userDetails.getUsername(),file);
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 작성 성공", boardRespDto), HttpStatus.OK);

    }

    //첨부파일 다운로드
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> fileDownload(@PathVariable("id") int boardId) throws IOException {
        BoardRespDto boardRespDto = boardService.getList(boardId);
        Path path = Paths.get(System.getProperty("user.dir") + "/src/main/resources/static" + boardRespDto.getFilepath());
        Resource resource = new InputStreamResource(Files.newInputStream(path));
        String encodedFilename = URLEncoder.encode(boardRespDto.getOriginal_filename(), StandardCharsets.UTF_8.toString());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                .body(resource);
    }
    //선택 게시물 수정
//    @Operation(summary = "선택한 게시물 수정")
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "게시물 수정 성공",
//                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
//            @ApiResponse(responseCode = "403", description = "로그인 후 수정하세요"),
//    })
//    public ResponseEntity<?> updateList(@PathVariable int id, @RequestBody BoardReqDto requestsDto )throws Exception{
//        boardService.updateList(id, requestsDto);
//        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 수정 성공", requestsDto), HttpStatus.OK);
//    }

    //선택한 게시물 삭제
    @Operation(summary = "선택한 게시물 삭제 ")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "삭제 성공",
                    content = {@Content(schema = @Schema(implementation = ResponseEntity.class))}),
            @ApiResponse(responseCode = "403", description = "로그인 후 삭제하세요"),
    })
    public ResponseEntity<?> deleteList(@PathVariable int id ){
        return new ResponseEntity<>(new ResponseDto<>(1, "게시물 삭제 성공", boardService.deleteList(id)), HttpStatus.OK);
    }
}
