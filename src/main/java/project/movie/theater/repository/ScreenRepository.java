package project.movie.theater.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.theater.domain.Screen;

public interface ScreenRepository extends JpaRepository<Screen, Long> {

}
