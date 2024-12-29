package project.movie.member.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MemberRole {
    GUEST("비회원"), CUSTOMER("일반회원");
    private String value;
}