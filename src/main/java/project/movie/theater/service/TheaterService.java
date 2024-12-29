package project.movie.theater.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.theater.domain.Theater;
import project.movie.theater.dto.TheaterResDto;
import project.movie.theater.repository.TheaterRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TheaterService {

    private final TheaterRepository theaterRepository;

    @Transactional(readOnly = true)
    public List<TheaterResDto> findAll() {
        List<TheaterResDto> theaterResList = null;

        List<Theater> theaterList = theaterRepository.findAll();
        theaterResList = theaterList.stream().map(TheaterResDto::from).collect(Collectors.toList());

        return theaterResList;
    }

}
