package project.movie.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByMemberId(String memberId);
}
