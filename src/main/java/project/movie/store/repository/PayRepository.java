package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.pay.Pay;

import java.util.List;
import java.util.Optional;

public interface PayRepository extends JpaRepository<Pay,String> {
    List<Pay> findByMember_memberId(String memberId);
    Optional<Pay> findByPayCode(String payCode);
}
