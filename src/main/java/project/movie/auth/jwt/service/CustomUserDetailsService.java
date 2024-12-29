package project.movie.auth.jwt.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.movie.auth.jwt.dto.CustomUserDetails;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;

import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository userRepository;

    public CustomUserDetailsService(MemberRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOP = userRepository.findByMemberId(username);

        if (memberOP.isPresent()) {
            log.info("유저가 존재합니다. 인증 처리 로직을 실행합니다.");
            return new CustomUserDetails(memberOP.get());
        }

        log.info("사용자를 찾을수 없습니다.");
        return null;
    }
}
