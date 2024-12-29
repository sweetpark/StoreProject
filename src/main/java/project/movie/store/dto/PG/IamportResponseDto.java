package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IamportResponseDto {
    private Integer code;
    private String message;
    private PaymentResponseDto response;
}
