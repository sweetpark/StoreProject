package project.movie.store.dto.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartItemRequestDto {
    private Integer itemCode;
    private Integer quantity;

    public CartItemRequestDto(){}
}
