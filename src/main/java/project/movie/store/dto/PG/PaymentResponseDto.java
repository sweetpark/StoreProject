package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private String merchant_uid; //payCode
    private String imp_uid;
    private String status;
    private int amount; // price
}
