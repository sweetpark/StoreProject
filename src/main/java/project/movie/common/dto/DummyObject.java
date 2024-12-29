package project.movie.common.dto;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.movie.auth.jwt.util.JWTUtil;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;

public class DummyObject {

    protected Member newMember(String username, String fullname) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encPassword = encoder.encode("1234");
        return Member.builder()
                .memberId(username)
                .username(username)
                .password(encPassword)
                .email(username + "@nate.com")
                .fullname(fullname)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    protected Member newMockMember(String memberId, String username, String fullname) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encPassword = encoder.encode("1234");
        return Member.builder()
                .memberId(memberId)
                .username(username)
                .password(encPassword)
                .email(username + "@naver.com")
                .fullname(fullname)
                .role(MemberRole.CUSTOMER)
                .status(MemberStatus.ACTIVE)
                .build();
    }

    public static String generateJwtToken(JWTUtil jwtUtil) {
        String token = jwtUtil.createJwt("net1506", MemberRole.CUSTOMER.toString(), 60 * 60 * 10L);
        return "Bearer " + token; // 생성된 토큰 반환
    }
}
