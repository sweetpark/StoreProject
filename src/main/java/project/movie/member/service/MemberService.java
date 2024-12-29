package project.movie.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.member.domain.Member;
import project.movie.member.dto.MemberRespDto;
import project.movie.member.dto.MemberSaveReqDto;
import project.movie.member.dto.MemberUpdateReqDto;
import project.movie.member.dto.PasswordChangeReqDto;
import project.movie.member.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public MemberRespDto create(MemberSaveReqDto memberSaveReqDto) {
        // 1. 동일한 유저 이름 존재 검사
        Optional<Member> memberOP = memberRepository.findByMemberId(memberSaveReqDto.getMemberId());

        if (memberOP.isPresent()) {
            // 아이디가 중복 되었다는 것
            throw new CustomApiException("동일한 아이디가 존재합니다.");
        }

        // 2. 패스워드인코딩 + 회원 가입
        Member member = memberRepository.save(memberSaveReqDto.to(bCryptPasswordEncoder));

        // 4. dto 응답
        return new MemberRespDto(member);
    }

    @Transactional
    public MemberRespDto update(String memberId, MemberUpdateReqDto memberUpdateReqDto) {
        // 1. 동일한 유저 이름 존재 검사
        Optional<Member> memberOP = memberRepository.findByMemberId(memberId);

        if (memberOP.isEmpty()) {
            // 변경할 아이디가 없다는 것
            throw new CustomApiException("아이디가 존재하지 않습니다.");
        }

        if (!memberOP.get().verifyOwnMemberId(memberId)) {
            // 자신의 계정이 아닌 것
            throw new CustomApiException("자신의 계정이 아닙니다.");
        }

        // 2. 패스워드인코딩 + 회원 정보 변경
        memberOP.get().update(memberUpdateReqDto);

        // 3. dto 응답
        return new MemberRespDto(memberUpdateReqDto.to(bCryptPasswordEncoder));
    }

    public Member getByMemberId(String memberId) {
        return memberRepository.findByMemberId(memberId)
                .orElseThrow(() -> new CustomApiException(memberId + " 는 존재하지 않는 사용자 입니다"));
    }

    @Transactional
    public void delete(String memberId, String password) {
        Member findMember = getByMemberId(memberId);
        boolean isValid = findMember.validatePassword(password, bCryptPasswordEncoder);
        if (!isValid) throw new CustomApiException("비밀번호가 일치하지 않습니다.");
        memberRepository.delete(findMember);
    }

    public List<Member> list() {
        return memberRepository.findAll();
    }

    @Transactional
    public void changePassword(String memberId, PasswordChangeReqDto passwordChangeReqDto) {
        Member findMember = getByMemberId(memberId);
        boolean isEqual = passwordChangeReqDto.checkPasswordAndConfirmPassword();
        if (!isEqual) throw new CustomApiException("새 비밀번호와 확인 비밀번호가 일치하지 않습니다.");
        boolean isValid = findMember.validatePassword(passwordChangeReqDto.getPassword(), bCryptPasswordEncoder);
        if (!isValid) throw new CustomApiException("현재 비밀번호가 일치하지 않습니다.");

        findMember.update(passwordChangeReqDto, bCryptPasswordEncoder); // 비밀번호 변경
    }
}
