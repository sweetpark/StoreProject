package project.movie.store.domain.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRequestDto {
    private String imp_key;
    private String imp_secret;

    public TokenRequestDto(String impKey, String impSecret) {
        this.imp_key = impKey;
        this.imp_secret = impSecret;
    }

}
