package project.movie.store.dto.PG;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentCompleteDto {
    private String impUid;
    private String payCode;
//    private String cartStatus;
}
