package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentRequestDto {
    private String payCode;
    private String impUid;
    private Integer totalPrice;
    private Integer status;
    private String memberUsername;
}
