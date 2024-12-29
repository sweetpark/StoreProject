package project.movie.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;

@Getter
@Setter
@ToString
@Builder
public class MemberRespDto {
    @Schema(description = "아이디", required = true, example = "jin8374")
    private String memberId;
    @Schema(description = "이름", required = true, example = "진경이")
    private String username;
    @Schema(description = "이메일", required = true, example = "park32122@naver.com")
    private String email;
    @Schema(description = "전화번호", required = true, example = "01066554444")
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
    private MemberRole role; // GUEST, CUSTOMER
    @Schema(description = "활동 여부", example = "ACTIVE | INACTIVE")
    private MemberStatus status; // 활동 여부

    public MemberRespDto(String memberId, String username, String email, String tel, String zipcode, String address, String detailAddress, String fullname, MemberRole role, MemberStatus status) {
        this.memberId = memberId;
        this.username = username;
        this.email = email;
        this.tel = tel;
        this.zipcode = zipcode;
        this.address = address;
        this.detailAddress = detailAddress;
        this.fullname = fullname;
        this.role = role;
        this.status = status;
    }

    public MemberRespDto(Member member) {
        this.memberId = member.getMemberId();
        this.username = member.getUsername();
        this.email = member.getEmail();
        this.tel = member.getTel();
        this.zipcode = member.getZipcode();
        this.address = member.getAddress();
        this.detailAddress = member.getDetailAddress();
        this.fullname = member.getFullname();
        this.role = member.getRole();
        this.status = member.getStatus();
    }

    public static MemberRespDto from(Member member) {
        return MemberRespDto.builder()
                .memberId(member.getMemberId())
                .email(member.getEmail())
                .username(member.getUsername())
                .email(member.getEmail())
                .tel(member.getTel())
                .zipcode(member.getZipcode())
                .address(member.getAddress())
                .fullname(member.getFullname())
                .role(member.getRole())
                .status(member.getStatus())
                .build();
    }

}
