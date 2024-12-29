package project.movie.movie.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PaymentMethod {
    CREDIT_CARD("신용카드"),
    MOBILE("휴대폰");
    private String value;
}
