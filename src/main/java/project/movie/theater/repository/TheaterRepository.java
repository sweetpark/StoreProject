package project.movie.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.theater.domain.Theater;

public interface TheaterRepository extends JpaRepository<Theater, Long> {

}
