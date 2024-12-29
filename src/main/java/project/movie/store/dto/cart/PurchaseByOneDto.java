package project.movie.store.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PurchaseByOneDto {
    private Integer itemCode;
    private Integer itemQty;
}
