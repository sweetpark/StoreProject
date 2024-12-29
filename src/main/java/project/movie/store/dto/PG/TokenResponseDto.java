package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {

    private int code;
    private String message;
    private TokenData response;  // 추가: 응답 안에 또 다른 데이터 구조를 포함

    @Getter
    @Setter
    public static class TokenData {
        private String access_token;  // 실제 access_token을 담는 필드
    }
}