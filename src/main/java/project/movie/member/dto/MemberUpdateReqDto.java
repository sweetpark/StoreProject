package project.movie.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MemberUpdateReqDto {
    @Schema(description = "아이디", required = true, example = "jin8374")
    @NotNull
    private String memberId;
//    @NotNull
//    @Size(min = 4, max = 20)
//    private String password;
    @Schema(description = "이름", required = true, example = "진경이")
    @NotNull
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,20}$", message = "한글/영문 1~20자 이내로 작성해주세요")
    private String username;
    @Schema(description = "이메일", required = true, example = "park32122@naver.com")
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9]{2,20}@[a-zA-Z0-9]{2,10}\\.[a-zA-Z]{2,3}$", message = "이메일 형식으로 작성해주세요")
    private String email;
    @Schema(description = "전화번호", required = true, example = "01066554444")
    @NotEmpty
    @Pattern(regexp = "^010[0-9]{8}$", message = "연락처는 '010'으로 시작하고 뒤에 8자리 숫자로 작성해주세요.")
    private String tel;
    @Schema(description = "우편코드", example = "417-888")
    private String zipcode;
    @Schema(description = "주소", example = "서울시 강남구 대치동")
    private String address;
    @Schema(description = "상세 주소", example = "66-1")
    private String detailAddress;
    @Schema(description = "전체 이름", example = "박진경")
    private String fullname;
    @Schema(description = "권한", example = "GUEST | CUSTOMER")
    @NotNull
    @Pattern(regexp = "GUEST|CUSTOMER", message = "유효하지 않은 역할입니다. GUEST 또는 CUSTOMER 중 하나를 선택하세요.")
    private String role; // GUEST, CUSTOMER
    @Schema(description = "활동 여부", example = "ACTIVE | INACTIVE")
    private MemberStatus status; // 활동 여부

    @Builder
    public MemberUpdateReqDto(Member member) {
        this.memberId = member.getMemberId();
//        this.password = member.getPassword();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.tel = member.getTel();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.detailAddress = member.getDetailAddress();
        this.fullname = member.getFullname();
        this.role = member.getRole().toString();
        this.status = member.getStatus();
    }

    public Member to(BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Member.builder()
                .memberId(this.memberId)
//                .password(bCryptPasswordEncoder.encode(password))
                .username(username)
                .email(email)
                .tel(tel)
                .zipcode(zipcode)
                .address(address)
                .detailAddress(detailAddress)
                .fullname(fullname)
                .role(MemberRole.valueOf(role))
                .status(status)
                .build();
    }
}
