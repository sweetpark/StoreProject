package project.movie.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import project.movie.board.domain.Board;
import project.movie.board.dto.BoardDto;
import project.movie.board.dto.BoardReqDto;
import project.movie.board.dto.BoardRespDto;
import project.movie.board.repository.BoardJpaRepository;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;
import project.movie.member.service.MemberService;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardJpaRepository boardRepository;
    private final MemberRepository memberRepository;
    // 전체 게시물 조회
    @Transactional(readOnly = true)
    public  List<BoardRespDto> getLists() {
        return boardRepository.findAll().stream()
                .map(BoardRespDto::new)
                .collect(Collectors.toList());
    }
    //선택한 게시물 조회
    @Transactional
    public BoardRespDto getList(int id) {
        return boardRepository.findById(id).map(BoardRespDto::new).orElseThrow(
                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
        );
    }
    //내가 작성한 게시물 조회
    @Transactional
    public List<BoardRespDto> getMyList(String userid) {
        return boardRepository.findByMember_MemberId(userid).stream()
                .map(BoardRespDto::new)
                .collect(Collectors.toList());
    }

    // 게시물 작성(첨부파일 없을때)
    @Transactional
    public BoardRespDto writeList(BoardReqDto requestsDto,String userId) {
        requestsDto.setMember(userId);
        requestsDto.setReg_date(LocalDateTime.now());
        Board board = new Board(requestsDto, memberRepository);
        boardRepository.save(board);
        return new BoardRespDto(board);
    }

    //첨부파일 있을때
    @Transactional
    public BoardRespDto writeListFile(BoardReqDto requestsDto,String userId, MultipartFile file) throws IOException {
        // 파일 업로드 처리 시작
        String projectPath = System.getProperty("user.dir") // 프로젝트 경로를 가져옴
                + "\\src\\main\\resources\\static\\files"; // 파일이 저장될 폴더의 경로

        UUID uuid = UUID.randomUUID(); // 랜덤으로 식별자를 생성

        String fileName = uuid + "_" + file.getOriginalFilename(); // UUID와 파일이름을 포함된 파일 이름으로 저장

        File saveFile = new File(projectPath, fileName); // projectPath는 위에서 작성한 경로, name은 전달받을 이름

        file.transferTo(saveFile);

        requestsDto.setOriginal_filename(file.getOriginalFilename());
        requestsDto.setStored_filename(fileName);
        requestsDto.setFilepath("/files/" + fileName); // static 아래부분의 파일 경로로만으로도 접근이 가능
        // 파일 업로드 처리 끝
        requestsDto.setMember(userId);
        requestsDto.setReg_date(LocalDateTime.now());

        Board board = new Board(requestsDto, memberRepository);
        boardRepository.save(board);

        return new BoardRespDto(board);
    }

    // 게시물 수정
    //@Transactional
//    public BoardRespDto updateList(int id, BoardReqDto requestsDto) throws Exception {
//        Board board = boardRepository.findById(id).orElseThrow(
//                () -> new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.")
//        );
//        requestsDto.setReg_date(LocalDateTime.now());
//        board.update(requestsDto);
//        return new BoardRespDto(board);
//    }


    // 게시글 삭제
    @Transactional
    public BoardRespDto deleteList(int id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("선택한 게시물이 존재하지 않습니다.");
        });

        // 게시글이 있는 경우 삭제처리
        boardRepository.deleteById(id);

        return new BoardRespDto(board);
    }


}
