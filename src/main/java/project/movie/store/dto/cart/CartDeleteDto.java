package project.movie.store.dto.cart;

import lombok.Getter;
import lombok.Setter;
import project.movie.store.domain.cart.CartStatus;

@Getter
@Setter
public class CartDeleteDto {

    private Integer cartCode;
    private Integer cartStatus;

    public CartDeleteDto(){};
}
