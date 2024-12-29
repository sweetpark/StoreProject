package project.movie.store.dto.cart;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CartUpdateDto {
    private Integer itemCode;
    private String cartCode;
    private Integer cartQty;
    private Integer cartStatus;
    private LocalDateTime cartDate;

    public CartUpdateDto(){}
}
